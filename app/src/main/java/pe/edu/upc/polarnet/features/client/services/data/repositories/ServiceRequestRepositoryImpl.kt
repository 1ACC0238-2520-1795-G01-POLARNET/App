package pe.edu.upc.polarnet.features.client.services.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.client.services.data.local.dao.ServiceRequestDao
import pe.edu.upc.polarnet.features.client.services.data.local.models.ServiceRequestEntity
import pe.edu.upc.polarnet.features.client.services.data.remote.services.ServiceRequestService
import pe.edu.upc.polarnet.features.client.services.domain.repositories.ServiceRequestRepository
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import javax.inject.Inject

class ServiceRequestRepositoryImpl @Inject constructor(
    private val service: ServiceRequestService,
    private val dao: ServiceRequestDao
) : ServiceRequestRepository {

    override suspend fun getAllServiceRequests(): List<ServiceRequest> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllServiceRequests()

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
                        equipment = null // puedes incluirlo luego si haces join
                    )
                } ?: emptyList()

                Log.d("ServiceRepo", "Solicitudes obtenidas: ${requests.size}")
                return@withContext requests
            } else {
                Log.e("ServiceRepo", "Error: ${response.code()} - ${response.message()}")
                Log.e("ServiceRepo", "Body: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("ServiceRepo", "Exception: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun getServiceRequestsByClient(clientId: Long): List<ServiceRequest> = withContext(Dispatchers.IO) {
        try {
            val response = service.getServiceRequestsByClient("eq.$clientId")

            if (response.isSuccessful) {
                return@withContext response.body()?.map { dto ->
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
                        equipment = null
                    )
                } ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("ServiceRepo", "Exception getting by client: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun insert(request: ServiceRequest) = withContext(Dispatchers.IO) {
        dao.insert(
            ServiceRequestEntity(
                id = request.id,
                clientId = request.clientId,
                equipmentId = request.equipmentId,
                requestType = request.requestType,
                description = request.description,
                startDate = request.startDate,
                endDate = request.endDate,
                status = request.status,
                totalPrice = request.totalPrice,
                notes = request.notes,
                createdAt = request.createdAt
            )
        )
    }

    override suspend fun delete(request: ServiceRequest) = withContext(Dispatchers.IO) {
        dao.delete(
            ServiceRequestEntity(
                id = request.id,
                clientId = request.clientId,
                equipmentId = request.equipmentId,
                requestType = request.requestType,
                description = request.description,
                startDate = request.startDate,
                endDate = request.endDate,
                status = request.status,
                totalPrice = request.totalPrice,
                notes = request.notes,
                createdAt = request.createdAt
            )
        )
    }
}
