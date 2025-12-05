package pe.edu.upc.polarnet.shared.models

data class Notification(
    val id: String,
    val userId: String,
    val role: String, // "client" o "provider"
    val type: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

