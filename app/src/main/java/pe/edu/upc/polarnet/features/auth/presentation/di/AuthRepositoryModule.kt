package pe.edu.upc.polarnet.features.auth.presentation.di


import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository

class AuthRepositoryModule : AuthRepository {
    override suspend fun login(email: String, password: String): User? = null
}