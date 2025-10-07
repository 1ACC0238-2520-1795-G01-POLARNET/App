package pe.edu.upc.polarnet.features.auth.domain.models

enum class UserRole {
    CLIENTE,    // Mapea a "user" de DummyJSON
    PROVEEDOR;  // Mapea a "admin" o "moderator" de DummyJSON

    companion object {
        fun fromString(role: String): UserRole {
            return when (role.lowercase()) {
                "admin", "moderator" -> PROVEEDOR
                "user" -> CLIENTE
                else -> CLIENTE // Valor por defecto
            }
        }
    }
}