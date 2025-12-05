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
                android.util.Log.d("AuthRepo", "====================================")
                android.util.Log.d("AuthRepo", "INICIANDO LOGIN")
                android.util.Log.d("AuthRepo", "====================================")
                android.util.Log.d("AuthRepo", "Email: $email")
                android.util.Log.d("AuthRepo", "URL Supabase: ${supabase.supabaseUrl}")

                val users = supabase
                    .from("users")
                    .select(columns = Columns.ALL) {
                        filter { eq("email", email) }
                    }
                    .decodeList<UserDetailDto>()

                android.util.Log.d("AuthRepo", "Usuarios encontrados: ${users.size}")

                if (users.isEmpty()) {
                    android.util.Log.d("AuthRepo", "No se encontró usuario con ese email")
                    return@withContext null
                }

                val userDto = users.first()
                android.util.Log.d("AuthRepo", "Usuario encontrado: ${userDto.fullName}")
                android.util.Log.d("AuthRepo", "Password en BD: ${userDto.password}")
                android.util.Log.d("AuthRepo", "Password ingresado: $password")
                android.util.Log.d("AuthRepo", "¿Coinciden?: ${userDto.password == password}")

                if (userDto.password != password) {
                    android.util.Log.d("AuthRepo", "Contraseña incorrecta")
                    return@withContext null
                }

                android.util.Log.d("AuthRepo", "Login exitoso para: ${userDto.fullName}")
                android.util.Log.d("AuthRepo", "ID del usuario: ${userDto.id}")
                android.util.Log.d("AuthRepo", "Role: ${userDto.role}")

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

                android.util.Log.d("AuthRepo", "User object creado con ID: ${user.id}")
                user
            } catch (e: Exception) {
                android.util.Log.e("AuthRepo", "====================================")
                android.util.Log.e("AuthRepo", "ERROR EN LOGIN")
                android.util.Log.e("AuthRepo", "====================================")
                android.util.Log.e("AuthRepo", "Mensaje: ${e.message}")
                android.util.Log.e("AuthRepo", "Tipo: ${e.javaClass.simpleName}")
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
