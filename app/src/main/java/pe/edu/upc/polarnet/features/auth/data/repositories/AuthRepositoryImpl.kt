package pe.edu.upc.polarnet.features.auth.data.repositories

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.core.networking.SupabaseClient
import pe.edu.upc.polarnet.features.auth.data.models.UserDetailDto
import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject // 👈 AÑADE ESTO

class AuthRepositoryImpl @Inject constructor( // 👈 AÑADE ESTO
) : AuthRepository {

    private val supabase = SupabaseClient.client

    override suspend fun login(email: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                println("🔍 Intentando login con email: $email")
                println("🔗 URL Supabase: ${supabase.supabaseUrl}")

                val users = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                if (users.isEmpty()) return@withContext null

                val userDto = users.first()
                if (userDto.password != password) return@withContext null

                println("✅ Login exitoso para: ${userDto.fullName}")

                User(
                    id = userDto.id,
                    fullName = userDto.fullName,
                    email = userDto.email,
                    password = password,
                    role = UserRole.fromString(userDto.role),
                    companyName = userDto.company,
                    phone = userDto.phone,
                    location = userDto.location,
                    createdAt = userDto.createdAt
                )
            } catch (e: Exception) {
                println("💥 Error en login: ${e.message}")
                e.printStackTrace()
                null
            }
        }
}
