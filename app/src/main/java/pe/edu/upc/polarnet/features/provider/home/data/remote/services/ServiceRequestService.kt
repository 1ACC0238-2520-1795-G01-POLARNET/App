package pe.edu.upc.polarnet.features.provider.home.data.remote.services

import com.google.gson.JsonObject
import pe.edu.upc.polarnet.features.provider.home.data.remote.models.ServiceRequestDto
import retrofit2.Response
import retrofit2.http.*

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

    // Actualizar estado de solicitud
    @PATCH("service_requests")
    suspend fun updateServiceRequestStatus(
        @Query("id") idQuery: String,
        @Body statusUpdate: JsonObject,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<ServiceRequestDto>>

    // Eliminar solicitud
    @DELETE("service_requests")
    suspend fun deleteServiceRequest(
        @Query("id") idQuery: String
    ): Response<Unit>

    // Crear registro en client_equipment
    @POST("client_equipment")
    suspend fun createClientEquipment(
        @Body clientEquipment: JsonObject,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<JsonObject>>
}