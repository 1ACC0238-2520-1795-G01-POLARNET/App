package pe.edu.upc.polarnet.features.provider.home.domain.repositories

import pe.edu.upc.polarnet.shared.models.ServiceRequest

interface ServiceRequestRepository {

    suspend fun getAllServiceRequests(): List<ServiceRequest>

    suspend fun getServiceRequestsByStatus(status: String): List<ServiceRequest>

    suspend fun getServiceRequestById(id: Long): ServiceRequest?

    suspend fun insert(serviceRequest: ServiceRequest)

    suspend fun delete(serviceRequest: ServiceRequest)

    suspend fun updateServiceRequestStatus(id: Long, status: String): Result<ServiceRequest>

    suspend fun deleteServiceRequest(id: Long): Result<Boolean>
}