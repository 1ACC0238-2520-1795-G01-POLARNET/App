package pe.edu.upc.polarnet.features.auth.domain.models

enum class UserRole {
    PROVIDER, CLIENT;

    companion object {
        fun fromString(role: String): UserRole {
            return when (role.lowercase()) {
                "provider" -> PROVIDER
                "client" -> CLIENT
                else -> CLIENT // valor por defecto
            }
        }
    }
}