package pe.edu.upc.polarnet.features.client.equipments.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_equipments")
data class ClientEquipmentEntity(
    @PrimaryKey val id: Long,
    val clientId: Long,
    val equipmentId: Long,
    val ownershipType: String, // "rented" o "owned"
    val startDate: String,
    val endDate: String?,
    val status: String, // "active", "returned", "pending"
    val notes: String?,
)
