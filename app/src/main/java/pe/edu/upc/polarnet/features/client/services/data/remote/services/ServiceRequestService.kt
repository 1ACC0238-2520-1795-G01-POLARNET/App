package pe.edu.upc.polarnet.features.client.services.data.remote.services

import pe.edu.upc.polarnet.features.client.services.data.remote.models.ServiceRequestDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceRequestService {

    @GET("service_requests")
    suspend fun getAllServiceRequests(
        @Query("select") select: String = "*"
    ): Response<List<ServiceRequestDto>>

    @GET("service_requests")
    suspend fun getServiceRequestsByClient(
        @Query("client_id") clientId: String,
        @Query("select") select: String = "*"
    ): Response<List<ServiceRequestDto>>

    @GET("service_requests")
    suspend fun getServiceRequestById(
        @Query("id") id: String,
        @Query("select") select: String = "*"
    ): Response<List<ServiceRequestDto>>
}
