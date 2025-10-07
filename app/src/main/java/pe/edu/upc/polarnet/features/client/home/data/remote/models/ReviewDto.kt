package pe.edu.upc.polarnet.features.client.home.data.remote.models

data class ReviewDto(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)
