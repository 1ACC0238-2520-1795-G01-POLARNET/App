package pe.edu.upc.polarnet.features.provider.add.data.remote.services

import pe.edu.upc.polarnet.features.client.home.data.remote.models.EquipmentDto
import pe.edu.upc.polarnet.features.provider.add.data.remote.models.CreateEquipmentDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddEquipmentService {

    @POST("equipment")
    suspend fun createEquipment(
        @Body equipment: CreateEquipmentDto
    ): Response<EquipmentDto>
}