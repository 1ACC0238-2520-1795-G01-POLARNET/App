package pe.edu.upc.polarnet.features.client.services.domain.repositories

import pe.edu.upc.polarnet.shared.models.ServiceRequest

interface ServiceRequestRepository {

    suspend fun getAllServiceRequests(): List<ServiceRequest>

    suspend fun getServiceRequestsByClient(clientId: Long): List<ServiceRequest>

    suspend fun insert(request: ServiceRequest)

    suspend fun delete(request: ServiceRequest)
}
