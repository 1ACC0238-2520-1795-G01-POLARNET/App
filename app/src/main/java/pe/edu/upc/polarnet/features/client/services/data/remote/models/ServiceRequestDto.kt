package pe.edu.upc.polarnet.features.client.services.data.remote.models

import com.google.gson.annotations.SerializedName

data class ServiceRequestDto(
    val id: Long,

    @SerializedName("client_id")
    val clientId: Long,

    @SerializedName("equipment_id")
    val equipmentId: Long,

    @SerializedName("request_type")
    val requestType: String, // "rental", "maintenance", "installation"

    val description: String?,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,

    val status: String, // "pending", "in_progress", "completed", "cancelled"

    @SerializedName("total_price")
    val totalPrice: Double,

    val notes: String?,

    @SerializedName("created_at")
    val createdAt: String?
)
