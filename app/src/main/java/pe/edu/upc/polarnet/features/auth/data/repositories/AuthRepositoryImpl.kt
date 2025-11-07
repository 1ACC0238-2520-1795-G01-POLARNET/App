package pe.edu.upc.polarnet.features.auth.data.repositories

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder  // ← CAMBIAR
import pe.edu.upc.polarnet.core.networking.SupabaseClient
import pe.edu.upc.polarnet.features.auth.data.models.UserDetailDto
import pe.edu.upc.polarnet.features.auth.domain.models.User
import pe.edu.upc.polarnet.features.auth.domain.models.UserRole
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val supabase = SupabaseClient.client
    private val passwordEncoder = BCryptPasswordEncoder()  // ← AGREGAR

    override suspend fun login(email: String, password: String): User? =
        withContext(Dispatchers.IO) {
            try {
                println("Intentando login con email: $email")

                val users = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                if (users.isEmpty()) {
                    println("Usuario no encontrado")
                    return@withContext null
                }

                val userDto = users.first()
                println("Usuario encontrado: ${userDto.fullName}")

                // Verificar password con Spring BCrypt
                if (!passwordEncoder.matches(password, userDto.password)) {
                    println("Password incorrecta")
                    return@withContext null
                }

                println("Login exitoso para: ${userDto.fullName}")

                User(
                    id = userDto.id ?: 0,
                    fullName = userDto.fullName,
                    email = userDto.email,
                    password = "",
                    role = UserRole.fromString(userDto.role),
                    companyName = userDto.company,
                    phone = userDto.phone,
                    location = userDto.location,
                    createdAt = userDto.createdAt
                )
            } catch (e: Exception) {
                println("Error en login: ${e.message}")
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
                println("Intentando registrar usuario")

                val existingUsers = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                if (existingUsers.isNotEmpty()) {
                    println("El email ya está registrado")
                    return@withContext null
                }

                // Encriptar password
                val hashedPassword = passwordEncoder.encode(password)

                val newUserDto = UserDetailDto(
                    id = null,
                    fullName = fullName,
                    email = email,
                    password = hashedPassword,
                    role = role.name.uppercase(),
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

                println("Usuario registrado: ${insertedUser.fullName}")

                User(
                    id = insertedUser.id ?: 0,
                    fullName = insertedUser.fullName,
                    email = insertedUser.email,
                    password = "",
                    role = UserRole.fromString(insertedUser.role),
                    companyName = insertedUser.company,
                    phone = insertedUser.phone,
                    location = insertedUser.location,
                    createdAt = insertedUser.createdAt
                )
            } catch (e: Exception) {
                println("Error en registro: ${e.message}")
                e.printStackTrace()
                null
            }
        }
}