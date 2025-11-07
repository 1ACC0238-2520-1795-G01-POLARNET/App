package pe.edu.upc.polarnet.features.client.equipments.data.remote.services

import pe.edu.upc.polarnet.features.client.equipments.data.remote.models.ClientEquipmentDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientEquipmentService {

    // 🔹 Obtener todos los equipos de todos los clientes (solo si es necesario)
    @GET("client_equipment")
    suspend fun getAllClientEquipments(
        @Query("select") select: String = "*,equipments(*)"
    ): Response<List<ClientEquipmentDto>>

    // 🔹 Obtener los equipos de un cliente específico
    @GET("client_equipment")
    suspend fun getClientEquipmentsByClientId(
        @Query("client_id") clientId: String,
        @Query("select") select: String = "*,equipments(*)"
    ): Response<List<ClientEquipmentDto>>

    // 🔹 Obtener un registro específico por ID (por si quieres ver detalle)
    @GET("client_equipment")
    suspend fun getClientEquipmentById(
        @Query("id") id: String,
        @Query("select") select: String = "*,equipments(*)"
    ): Response<List<ClientEquipmentDto>>
}
