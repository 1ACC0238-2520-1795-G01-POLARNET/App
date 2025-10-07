package pe.edu.upc.polarnet.features.client.home.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipments")
data class EquipmentEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val category: String,
    val description: String?,
    val thumbnail: String?,
    val price_per_month: Double,
    val purchase_price: Double,
    val available: Boolean,
    val location: String?
)
