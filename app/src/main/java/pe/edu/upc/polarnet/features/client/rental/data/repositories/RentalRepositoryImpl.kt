package pe.edu.upc.polarnet.features.client.rental.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.client.rental.data.remote.models.CreateServiceRequestDto
import pe.edu.upc.polarnet.features.client.rental.data.remote.services.RentalService
import pe.edu.upc.polarnet.features.client.rental.domain.repositories.RentalRepository
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import javax.inject.Inject

class RentalRepositoryImpl @Inject constructor(
    private val service: RentalService
) : RentalRepository {

    override suspend fun createRentalRequest(serviceRequest: ServiceRequest): Result<ServiceRequest> = withContext(Dispatchers.IO) {
        try {
            Log.d("RentalRepo", "====================================")
            Log.d("RentalRepo", "ENVIANDO SOLICITUD A SUPABASE")
            Log.d("RentalRepo", "====================================")
            Log.d("RentalRepo", "client_id: ${serviceRequest.clientId}")
            Log.d("RentalRepo", "equipment_id: ${serviceRequest.equipmentId}")
            Log.d("RentalRepo", "request_type: ${serviceRequest.requestType}")
            Log.d("RentalRepo", "total_price: ${serviceRequest.totalPrice}")
            Log.d("RentalRepo", "start_date: ${serviceRequest.startDate}")
            Log.d("RentalRepo", "end_date: ${serviceRequest.endDate}")

            val requestDto = CreateServiceRequestDto(
                clientId = serviceRequest.clientId,
                equipmentId = serviceRequest.equipmentId,
                requestType = serviceRequest.requestType,
                description = serviceRequest.description,
                startDate = serviceRequest.startDate ?: "",
                endDate = serviceRequest.endDate,
                totalPrice = serviceRequest.totalPrice,
                notes = serviceRequest.notes
            )

            Log.d("RentalRepo", "DTO creado con client_id: ${requestDto.clientId}")

            val response = service.createRentalRequest(requestDto)

            if (response.isSuccessful) {
                val dtoList = response.body()
                if (!dtoList.isNullOrEmpty()) {
                    val dto = dtoList[0] // Supabase devuelve un array con 1 elemento
                    Log.d("RentalRepo", "Solicitud creada exitosamente: ID ${dto.id}")

                    val createdRequest = ServiceRequest(
                        id = dto.id,
                        clientId = dto.clientId,
                        equipmentId = dto.equipmentId,
                        requestType = dto.requestType,
                        description = dto.description,
                        startDate = dto.startDate,
                        endDate = dto.endDate,
                        status = dto.status,
                        totalPrice = dto.totalPrice,
                        notes = dto.notes,
                        createdAt = dto.createdAt,
                        equipment = null,
                        client = null
                    )

                    return@withContext Result.success(createdRequest)
                } else {
                    Log.e("RentalRepo", "Body vacío en la respuesta")
                    return@withContext Result.failure(Exception("Error: Respuesta vacía del servidor"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("RentalRepo", "Error ${response.code()}: $errorBody")
                return@withContext Result.failure(Exception("Error al crear solicitud: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("RentalRepo", "Excepción al crear solicitud: ${e.message}", e)
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getRentalRequestsByClient(clientId: Long): Result<List<ServiceRequest>> = withContext(Dispatchers.IO) {
        try {
            Log.d("RentalRepo", "Obteniendo solicitudes del cliente: $clientId")

            val response = service.getRentalRequestsByClient("eq.$clientId")

            if (response.isSuccessful) {
                val requests = response.body()?.map { dto ->
                    ServiceRequest(
                        id = dto.id,
                        clientId = dto.clientId,
                        equipmentId = dto.equipmentId,
                        requestType = dto.requestType,
                        description = dto.description,
                        startDate = dto.startDate,
                        endDate = dto.endDate,
                        status = dto.status,
                        totalPrice = dto.totalPrice,
                        notes = dto.notes,
                        createdAt = dto.createdAt,
                        equipment = null,
                        client = null
                    )
                } ?: emptyList()

                Log.d("RentalRepo", "Solicitudes obtenidas: ${requests.size}")
                return@withContext Result.success(requests)
            } else {
                Log.e("RentalRepo", "Error ${response.code()}: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception("Error al obtener solicitudes"))
            }
        } catch (e: Exception) {
            Log.e("RentalRepo", "Excepción: ${e.message}", e)
            return@withContext Result.failure(e)
        }
    }
}

