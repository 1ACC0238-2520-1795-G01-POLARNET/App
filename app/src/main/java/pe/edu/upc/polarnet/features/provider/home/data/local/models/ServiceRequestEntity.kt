package pe.edu.upc.polarnet.features.provider.home.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provider_service_requests")
data class ServiceRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val clientId: Long,
    val equipmentId: Long,
    val requestType: String,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String,
    val totalPrice: Double,
    val notes: String?,
    val createdAt: String?
)
