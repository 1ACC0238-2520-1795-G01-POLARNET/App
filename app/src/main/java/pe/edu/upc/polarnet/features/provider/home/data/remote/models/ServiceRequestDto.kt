package pe.edu.upc.polarnet.features.provider.home.data.remote.models

import com.google.gson.annotations.SerializedName
import pe.edu.upc.polarnet.features.client.home.data.remote.models.EquipmentDto

data class ServiceRequestDto(
    val id: Long,

    @SerializedName("client_id")
    val clientId: Long,

    @SerializedName("equipment_id")
    val equipmentId: Long,

    @SerializedName("request_type")
    val requestType: String,

    val description: String?,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    val status: String,

    @SerializedName("total_price")
    val totalPrice: Double,

    val notes: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    // Relaciones embebidas (Supabase puede devolver esto con ?select=*,equipment(*),client:users(*))
    val equipment: EquipmentDto?,

    @SerializedName("client")
    val client: UserDto?
)