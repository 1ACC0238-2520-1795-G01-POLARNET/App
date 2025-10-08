package pe.edu.upc.polarnet.features.provider.add.data.remote.models

import com.google.gson.annotations.SerializedName

data class CreateEquipmentDto(
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
    val purchasePrice: Double
)