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

class AuthRepositoryImpl : AuthRepository {

    private val supabase = SupabaseClient.client

    override suspend fun login(email: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                // Log para debug
                println("🔍 Intentando login con email: $email")
                println("🔗 URL Supabase: ${supabase.supabaseUrl}")

                // Primero intenta obtener TODOS los usuarios para debug
                val allUsers = try {
                    supabase
                        .from("users")
                        .select(columns = Columns.ALL)
                        .decodeList<UserDetailDto>()
                } catch (e: Exception) {
                    println("⚠️ Error al obtener todos los usuarios: ${e.message}")
                    emptyList()
                }

                println("📊 Total usuarios en BD: ${allUsers.size}")
                allUsers.forEach { println("   - ${it.email}") }

                // Buscar usuario por email
                val users = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter {
                            eq("email", email)
                        }
                    }
                    .decodeList<UserDetailDto>()

                println("📊 Usuarios encontrados con filtro: ${users.size}")

                if (users.isEmpty()) {
                    println("❌ No se encontró usuario con email: $email")
                    return@withContext null
                }

                val userDto = users.firstOrNull()

                // Verificar contraseña
                if (userDto?.password != password) {
                    println("❌ Contraseña incorrecta")
                    return@withContext null
                }

                println("✅ Login exitoso para: ${userDto.fullName}")

                // Retornar usuario
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