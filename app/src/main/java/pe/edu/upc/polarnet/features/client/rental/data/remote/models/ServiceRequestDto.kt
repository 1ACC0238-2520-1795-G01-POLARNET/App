package pe.edu.upc.polarnet.features.client.rental.data.remote.models

import com.google.gson.annotations.SerializedName

data class ServiceRequestDto(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("client_id")
    val clientId: Long,

    @SerializedName("equipment_id")
    val equipmentId: Long,

    @SerializedName("request_type")
    val requestType: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String? = null,

    @SerializedName("status")
    val status: String = "pending",

    @SerializedName("total_price")
    val totalPrice: Double,

    @SerializedName("notes")
    val notes: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)

data class CreateServiceRequestDto(
    @SerializedName("client_id")
    val clientId: Long,

    @SerializedName("equipment_id")
    val equipmentId: Long,

    @SerializedName("request_type")
    val requestType: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String? = null,

    @SerializedName("total_price")
    val totalPrice: Double,

    @SerializedName("notes")
    val notes: String? = null
)

