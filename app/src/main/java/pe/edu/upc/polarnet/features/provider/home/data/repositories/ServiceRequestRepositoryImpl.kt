package pe.edu.upc.polarnet.features.provider.home.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.provider.home.data.local.dao.ServiceRequestDao
import pe.edu.upc.polarnet.features.provider.home.data.local.models.ServiceRequestEntity
import pe.edu.upc.polarnet.features.provider.home.data.remote.services.ServiceRequestService
import pe.edu.upc.polarnet.features.provider.home.domain.repositories.ServiceRequestRepository
import pe.edu.upc.polarnet.shared.models.Equipment
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
                val serviceRequests = response.body()?.map { dto ->
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
                        equipment = dto.equipment?.let {
                            Equipment(
                                id = it.id,
                                providerId = it.providerId,
                                name = it.name,
                                brand = it.brand,
                                model = it.model,
                                category = it.category,
                                description = it.description,
                                thumbnail = it.thumbnail,
                                specifications = it.specifications,
                                available = it.available,
                                location = it.location,
                                pricePerMonth = it.pricePerMonth,
                                purchasePrice = it.purchasePrice,
                                createdAt = it.createdAt,
                                updatedAt = it.updatedAt
                            )
                        },
                        client = dto.client?.let {
                            User(
                                id = it.id,
                                fullName = it.fullName,
                                email = it.email,
                                password = "", // No exponemos password
                                role = UserRole.fromString(it.role),
                                companyName = it.companyName,
                                phone = it.phone,
                                location = it.location,
                                createdAt = it.createdAt
                            )
                        }
                    )
                } ?: emptyList()

                Log.d("ServiceRequestRepo", "Solicitudes obtenidas: ${serviceRequests.size}")
                return@withContext serviceRequests
            } else {
                Log.e("ServiceRequestRepo", "Error: ${response.code()} - ${response.message()}")
                Log.e("ServiceRequestRepo", "Body: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("ServiceRequestRepo", "Exception: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun getServiceRequestsByStatus(status: String): List<ServiceRequest> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getServiceRequestsByStatus(status)

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
                            equipment = dto.equipment?.let {
                                Equipment(
                                    id = it.id,
                                    providerId = it.providerId,
                                    name = it.name,
                                    brand = it.brand,
                                    model = it.model,
                                    category = it.category,
                                    description = it.description,
                                    thumbnail = it.thumbnail,
                                    specifications = it.specifications,
                                    available = it.available,
                                    location = it.location,
                                    pricePerMonth = it.pricePerMonth,
                                    purchasePrice = it.purchasePrice,
                                    createdAt = it.createdAt,
                                    updatedAt = it.updatedAt
                                )
                            },
                            client = dto.client?.let {
                                User(
                                    id = it.id,
                                    fullName = it.fullName,
                                    email = it.email,
                                    password = "",
                                    role = UserRole.fromString(it.role),
                                    companyName = it.companyName,
                                    phone = it.phone,
                                    location = it.location,
                                    createdAt = it.createdAt
                                )
                            }
                        )
                    } ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("ServiceRequestRepo", "Exception: ${e.message}", e)
            }

            return@withContext emptyList()
        }

    override suspend fun getServiceRequestById(id: Long): ServiceRequest? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getServiceRequestById("eq.$id")

                if (response.isSuccessful) {
                    response.body()?.firstOrNull()?.let { dto ->
                        return@withContext ServiceRequest(
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
                            equipment = dto.equipment?.let {
                                Equipment(
                                    id = it.id,
                                    providerId = it.providerId,
                                    name = it.name,
                                    brand = it.brand,
                                    model = it.model,
                                    category = it.category,
                                    description = it.description,
                                    thumbnail = it.thumbnail,
                                    specifications = it.specifications,
                                    available = it.available,
                                    location = it.location,
                                    pricePerMonth = it.pricePerMonth,
                                    purchasePrice = it.purchasePrice,
                                    createdAt = it.createdAt,
                                    updatedAt = it.updatedAt
                                )
                            },
                            client = dto.client?.let {
                                User(
                                    id = it.id,
                                    fullName = it.fullName,
                                    email = it.email,
                                    password = "",
                                    role = UserRole.fromString(it.role),
                                    companyName = it.companyName,
                                    phone = it.phone,
                                    location = it.location,
                                    createdAt = it.createdAt
                                )
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("ServiceRequestRepo", "Exception: ${e.message}", e)
            }

            return@withContext null
        }

    override suspend fun insert(serviceRequest: ServiceRequest) = withContext(Dispatchers.IO) {
        dao.insert(
            ServiceRequestEntity(
                id = serviceRequest.id,
                clientId = serviceRequest.clientId,
                equipmentId = serviceRequest.equipmentId,
                requestType = serviceRequest.requestType,
                description = serviceRequest.description,
                startDate = serviceRequest.startDate,
                endDate = serviceRequest.endDate,
                status = serviceRequest.status,
                totalPrice = serviceRequest.totalPrice,
                notes = serviceRequest.notes,
                createdAt = serviceRequest.createdAt
            )
        )
    }

    override suspend fun delete(serviceRequest: ServiceRequest) = withContext(Dispatchers.IO) {
        dao.delete(
            ServiceRequestEntity(
                id = serviceRequest.id,
                clientId = serviceRequest.clientId,
                equipmentId = serviceRequest.equipmentId,
                requestType = serviceRequest.requestType,
                description = serviceRequest.description,
                startDate = serviceRequest.startDate,
                endDate = serviceRequest.endDate,
                status = serviceRequest.status,
                totalPrice = serviceRequest.totalPrice,
                notes = serviceRequest.notes,
                createdAt = serviceRequest.createdAt
            )
        )
    }

    override suspend fun updateServiceRequestStatus(id: Long, status: String): Result<ServiceRequest> =
        withContext(Dispatchers.IO) {
            try {
                Log.d("ServiceRequestRepo", "====================================")
                Log.d("ServiceRequestRepo", "ACTUALIZANDO ESTADO DE SOLICITUD")
                Log.d("ServiceRequestRepo", "====================================")
                Log.d("ServiceRequestRepo", "ID: $id")
                Log.d("ServiceRequestRepo", "Nuevo estado: $status")

                val statusUpdate = com.google.gson.JsonObject().apply {
                    addProperty("status", status)
                }

                val response = service.updateServiceRequestStatus(
                    idQuery = "eq.$id",
                    statusUpdate = statusUpdate
                )

                if (response.isSuccessful) {
                    val dtoList = response.body()
                    if (!dtoList.isNullOrEmpty()) {
                        val dto = dtoList[0]
                        Log.d("ServiceRequestRepo", "Estado actualizado exitosamente")

                        val updatedRequest = ServiceRequest(
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
                            equipment = dto.equipment?.let {
                                Equipment(
                                    id = it.id,
                                    providerId = it.providerId,
                                    name = it.name,
                                    brand = it.brand,
                                    model = it.model,
                                    category = it.category,
                                    description = it.description,
                                    thumbnail = it.thumbnail,
                                    specifications = it.specifications,
                                    available = it.available,
                                    location = it.location,
                                    pricePerMonth = it.pricePerMonth,
                                    purchasePrice = it.purchasePrice,
                                    createdAt = it.createdAt,
                                    updatedAt = it.updatedAt
                                )
                            },
                            client = dto.client?.let {
                                User(
                                    id = it.id,
                                    fullName = it.fullName,
                                    email = it.email,
                                    password = "",
                                    role = UserRole.fromString(it.role),
                                    companyName = it.companyName,
                                    phone = it.phone,
                                    location = it.location,
                                    createdAt = it.createdAt
                                )
                            }
                        )

                        return@withContext Result.success(updatedRequest)
                    } else {
                        Log.e("ServiceRequestRepo", "Respuesta vacía")
                        return@withContext Result.failure(Exception("Error: Respuesta vacía del servidor"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ServiceRequestRepo", "Error ${response.code()}: $errorBody")
                    return@withContext Result.failure(Exception("Error al actualizar: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("ServiceRequestRepo", "Excepción: ${e.message}", e)
                return@withContext Result.failure(e)
            }
        }

    override suspend fun deleteServiceRequest(id: Long): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                Log.d("ServiceRequestRepo", "====================================")
                Log.d("ServiceRequestRepo", "ELIMINANDO SOLICITUD")
                Log.d("ServiceRequestRepo", "====================================")
                Log.d("ServiceRequestRepo", "ID: $id")

                val response = service.deleteServiceRequest(idQuery = "eq.$id")

                if (response.isSuccessful) {
                    Log.d("ServiceRequestRepo", "Solicitud eliminada exitosamente")
                    return@withContext Result.success(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ServiceRequestRepo", "Error ${response.code()}: $errorBody")
                    return@withContext Result.failure(Exception("Error al eliminar: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("ServiceRequestRepo", "Excepción: ${e.message}", e)
                return@withContext Result.failure(e)
            }
        }
}