package pe.edu.upc.polarnet.features.client.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class EquipmentDto(
    val id: Long,

    @SerializedName("provider_id")
    val providerId: Long,

    val name: String,
    val brand: String?,
    val model: String?,
    val category: String,
    val description: String?,
    val thumbnail: String?,
    val specifications: Map<String, String>?,
    val available: Boolean,
    val location: String?,

    @SerializedName("price_per_month")
    val pricePerMonth: Double,

    @SerializedName("purchase_price")
    val purchasePrice: Double,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
)