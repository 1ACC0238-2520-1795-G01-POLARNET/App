package pe.edu.upc.polarnet.features.auth.data.models

data class LoginRequestDto(
    val email: String,
    val password: String
)