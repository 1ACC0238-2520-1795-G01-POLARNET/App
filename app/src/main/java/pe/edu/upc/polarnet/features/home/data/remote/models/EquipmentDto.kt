package pe.edu.upc.polarnet.features.home.data.remote.models

data class EquipmentDto(
    val id: Long,
    val providerId: Long,
    val name: String,
    val brand: String?,
    val model: String?,
    val category: String,
    val description: String?,
    val thumbnail: String?,
    val images: List<String>?,
    val specifications: Map<String, String>?,
    val dimensions: DimensionsDto?,
    val weight: Double?,
    val meta: MetaDto?,
    val available: Boolean,
    val location: String?,
    val pricePerMonth: Double,
    val purchasePrice: Double,
    val rating: Double?,
    val reviews: List<ReviewDto>?,
)
