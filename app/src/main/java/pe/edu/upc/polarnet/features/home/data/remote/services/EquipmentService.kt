package pe.edu.upc.polarnet.features.home.data.remote.services

import pe.edu.upc.polarnet.features.home.data.remote.models.EquipmentDto
import pe.edu.upc.polarnet.features.home.data.remote.models.EquipmentsWrapperDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EquipmentService {

    @GET("equipments")
    suspend fun getAllEquipments(): Response<EquipmentsWrapperDto>

    @GET("equipments/{id}")
    suspend fun getEquipmentById(@Path("id") id: Long): Response<EquipmentDto>
}
