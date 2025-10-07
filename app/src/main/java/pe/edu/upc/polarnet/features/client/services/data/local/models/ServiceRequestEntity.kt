package pe.edu.upc.polarnet.features.client.services.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_requests")
data class ServiceRequestEntity(
    @PrimaryKey val id: Long,
    val clientId: Long,
    val equipmentId: Long,
    val requestType: String, // "rental", "maintenance", "installation"
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String, // "pending", "in_progress", "completed", "cancelled"
    val totalPrice: Double,
    val notes: String?,
    val createdAt: String?
)
