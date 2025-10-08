package pe.edu.upc.polarnet.shared.models

import pe.edu.upc.polarnet.features.auth.domain.models.User

data class ServiceRequest(
    val id: Long,
    val clientId: Long,
    val equipmentId: Long,
    val requestType: String, // "rental", "maintenance", "installation"
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String, // "pending", "in_progress", "completed", "cancelled"
    val totalPrice: Double,
    val notes: String?,
    val createdAt: String?,
    // Relación con Equipment
    val equipment: Equipment?,
    val client: User?
)