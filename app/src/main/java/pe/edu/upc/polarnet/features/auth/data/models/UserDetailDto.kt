package pe.edu.upc.polarnet.features.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("role")
    val role: String,
    @SerialName("company_name")
    val company: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)
