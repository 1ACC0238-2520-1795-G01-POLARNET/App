package pe.edu.upc.polarnet.features.auth.domain.models

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val image: String,
    val role: UserRole,
    val accessToken: String
)