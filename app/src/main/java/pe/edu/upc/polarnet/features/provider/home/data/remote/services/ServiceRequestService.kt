package pe.edu.upc.polarnet.features.provider.home.data.remote.services

import pe.edu.upc.polarnet.features.provider.home.data.remote.models.ServiceRequestDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceRequestService {

    // Obtener todas las solicitudes con relaciones (equipment y client)
    @GET("service_requests")
    suspend fun getAllServiceRequests(
        @Query("select") select: String = "*,equipment(*),client:users!client_id(*)"
    ): Response<List<ServiceRequestDto>>

    // Filtrar por status
    @GET("service_requests")
    suspend fun getServiceRequestsByStatus(
        @Query("status") status: String,
        @Query("select") select: String = "*,equipment(*),client:users!client_id(*)"
    ): Response<List<ServiceRequestDto>>

    @GET("service_requests")
    suspend fun getServiceRequestById(
        @Query("id") id: String,
        @Query("select") select: String = "*,equipment(*),client:users!client_id(*)"
    ): Response<List<ServiceRequestDto>>
}