package pe.edu.upc.polarnet.features.auth.data.models

data class UserDetailDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val gender: String,
    val image: String,
    val role: String // admin, moderator, user
)