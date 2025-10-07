package pe.edu.upc.polarnet.features.client.home.data.remote.services

import pe.edu.upc.polarnet.features.client.home.data.remote.models.EquipmentDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EquipmentService {

    // Supabase REST API devuelve directamente un array JSON
    @GET("equipment")
    suspend fun getAllEquipments(
        @Query("select") select: String = "*"
    ): Response<List<EquipmentDto>>

    @GET("equipment")
    suspend fun getEquipmentById(
        @Query("id") id: String,
        @Query("select") select: String = "*"
    ): Response<List<EquipmentDto>>
}