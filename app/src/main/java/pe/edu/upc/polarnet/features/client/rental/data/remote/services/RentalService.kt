package pe.edu.upc.polarnet.features.client.rental.data.remote.services

import pe.edu.upc.polarnet.features.client.rental.data.remote.models.CreateServiceRequestDto
import pe.edu.upc.polarnet.features.client.rental.data.remote.models.ServiceRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RentalService {

    @POST("/rest/v1/service_requests")
    suspend fun createRentalRequest(
        @Body request: CreateServiceRequestDto,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<ServiceRequestDto>>

    @GET("/rest/v1/service_requests")
    suspend fun getRentalRequestsByClient(
        @Query("client_id") clientId: String
    ): Response<List<ServiceRequestDto>>
}

