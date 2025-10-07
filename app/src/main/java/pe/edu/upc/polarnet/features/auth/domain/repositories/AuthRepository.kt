package pe.edu.upc.polarnet.features.auth.domain.repositories

import io.github.jan.supabase.auth.OtpType
import pe.edu.upc.polarnet.features.auth.domain.models.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User?
}
