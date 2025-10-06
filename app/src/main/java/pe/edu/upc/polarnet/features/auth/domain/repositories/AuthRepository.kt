package pe.edu.upc.polarnet.features.auth.domain.repositories

import pe.edu.upc.polarnet.features.auth.domain.models.User

interface AuthRepository {
    suspend fun login(username: String, password: String): User?
}