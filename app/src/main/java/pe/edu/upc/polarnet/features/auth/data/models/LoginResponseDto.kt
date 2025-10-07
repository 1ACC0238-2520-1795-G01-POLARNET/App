package pe.edu.upc.polarnet.features.auth.data.models

data class LoginResponseDto(
    val user: UserDetailDto,
    val token: String? = null
)