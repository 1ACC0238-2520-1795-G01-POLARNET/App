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

class AuthRepositoryImpl @Inject constructor(
) : AuthRepository {

    private val supabase = SupabaseClient.client

    override suspend fun login(email: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                println(" Intentando login con email: $email")
                println(" URL Supabase: ${supabase.supabaseUrl}")

                val users = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                if (users.isEmpty()) return@withContext null

                val userDto = users.first()
                if (userDto.password != password) return@withContext null

                println(" Login exitoso para: ${userDto.fullName}")
                println(" ID del usuario: ${userDto.id}")
                println(" Email: ${userDto.email}")
                println(" Role: ${userDto.role}")

                val user = User(
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

                println(" User object creado con ID: ${user.id}")
                user
            } catch (e: Exception) {
                println(" Error en login: ${e.message}")
                e.printStackTrace()
                null
            }
        }

    override suspend fun register(
        email: String,
        password: String,
        fullName: String,
        role: UserRole,
        companyName: String?,
        phone: String?,
        location: String?
    ): User? =
        withContext(Dispatchers.IO) {
            try {
                println(" Intentando registrar usuario con email: $email como ${role.name}")

                // Verificar si el email ya existe
                val existingUsers = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                if (existingUsers.isNotEmpty()) {
                    println(" El email ya está registrado")
                    return@withContext null
                }

                // Crear nuevo usuario con todos los campos
                val newUserDto = UserDetailDto(
                    id = null, // Se auto-genera en la BD
                    fullName = fullName,
                    email = email,
                    password = password,
                    role = role.name.lowercase(),
                    company = companyName,
                    phone = phone,
                    location = location,
                    createdAt = null
                )

                val insertedUser = supabase
                    .from("users")
                    .insert(newUserDto) {
                        select()
                    }
                    .decodeSingle<UserDetailDto>()

                println(" Usuario registrado exitosamente: ${insertedUser.fullName} como ${insertedUser.role}")

                User(
                    id = insertedUser.id,
                    fullName = insertedUser.fullName,
                    email = insertedUser.email,
                    password = password,
                    role = UserRole.fromString(insertedUser.role),
                    companyName = insertedUser.company,
                    phone = insertedUser.phone,
                    location = insertedUser.location,
                    createdAt = insertedUser.createdAt
                )
            } catch (e: Exception) {
                println(" Error en registro: ${e.message}")
                e.printStackTrace()
                null
            }
        }
}
