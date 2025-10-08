package pe.edu.upc.polarnet.features.provider.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Long,

    @SerializedName("full_name")
    val fullName: String,

    val email: String,
    val role: String,

    @SerializedName("company_name")
    val companyName: String?,

    val phone: String?,
    val location: String?,

    @SerializedName("created_at")
    val createdAt: String?
)