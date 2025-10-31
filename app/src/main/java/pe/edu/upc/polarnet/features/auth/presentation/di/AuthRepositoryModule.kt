package pe.edu.upc.polarnet.features.auth.presentation.di


import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository

class AuthRepositoryModule : AuthRepository {
    override suspend fun login(email: String, password: String): User? = null
    override suspend fun register(
        email: String,
        password: String,
        fullName: String,
        role: UserRole,
        companyName: String?,
        phone: String?,
        location: String?
    ): User? {
        TODO("Not yet implemented")
    }


}