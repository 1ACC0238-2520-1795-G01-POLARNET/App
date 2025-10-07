package pe.edu.upc.polarnet.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val id: Long,
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
    val pricePerMonth: Double,
    val purchasePrice: Double,
    val createdAt: String?,
    val updatedAt: String?
)

