package pe.edu.upc.polarnet.features.client.equipments.data.remote.models

import com.google.gson.annotations.SerializedName
import pe.edu.upc.polarnet.features.client.home.data.remote.models.EquipmentDto

data class ClientEquipmentDto(
    val id: Long,

    @SerializedName("client_id")
    val clientId: Long,

    @SerializedName("equipment_id")
    val equipmentId: Long,

    @SerializedName("ownership_type")
    val ownershipType: String, // "rented" o "owned"

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String?,

    val status: String, // "active", "returned", "pending"

    val notes: String?,

    // Relación con Equipment (viene anidado si haces select(*, equipment(*)))
    val equipment: EquipmentDto?
)
