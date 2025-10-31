package pe.edu.upc.polarnet.features.auth.domain.repositories

import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole

interface AuthRepository {
    suspend fun login(email: String, password: String): User?
    suspend fun register(
        email: String,
        password: String,
        fullName: String,
        role: UserRole,
        companyName: String?,
        phone: String?,
        location: String?
    ): User?
}
