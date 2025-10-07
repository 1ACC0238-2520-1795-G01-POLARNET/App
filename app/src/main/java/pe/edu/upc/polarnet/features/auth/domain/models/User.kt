package pe.edu.upc.polarnet.features.auth.domain.models

data class User(
    val id: Long,
    val fullName: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val companyName: String?,
    val phone: String?,
    val location: String?,
    val createdAt: String? // o LocalDateTime si quieres parsear
)