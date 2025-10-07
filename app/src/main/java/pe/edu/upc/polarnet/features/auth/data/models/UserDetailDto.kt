package pe.edu.upc.polarnet.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    val id: Long,
    @SerialName("full_name")
    val fullName: String,
    val email: String,
    val password: String,
    val role: String,
    @SerialName("company_name")
    val company: String? = null,
    val phone: String? = null,
    val location: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)
