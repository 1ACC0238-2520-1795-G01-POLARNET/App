package pe.edu.upc.polarnet.features.auth.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.auth.data.models.LoginRequestDto
import pe.edu.upc.polarnet.features.auth.data.remote.AuthService
import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val service: AuthService) : AuthRepository {
    override suspend fun login(username: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                // Paso 1: Login para obtener el accessToken
                val loginResponse = service.login(LoginRequestDto(username, password))

                if (loginResponse.isSuccessful) {
                    val loginData = loginResponse.body() ?: return@withContext null

                    // Paso 2: Obtener datos completos del usuario (incluyendo role)
                    val userResponse = service.getCurrentUser("Bearer ${loginData.accessToken}")

                    if (userResponse.isSuccessful) {
                        val userData = userResponse.body() ?: return@withContext null

                        return@withContext User(
                            id = userData.id,
                            name = "${userData.lastName}, ${userData.firstName}",
                            image = userData.image,
                            username = userData.username,
                            role = UserRole.fromString(userData.role),
                            accessToken = loginData.accessToken
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext null
        }
}