package pe.edu.upc.polarnet.features.client.rental.domain.repositories

import pe.edu.upc.polarnet.shared.models.ServiceRequest

interface RentalRepository {
    suspend fun createRentalRequest(serviceRequest: ServiceRequest): Result<ServiceRequest>
    suspend fun getRentalRequestsByClient(clientId: Long): Result<List<ServiceRequest>>
}

