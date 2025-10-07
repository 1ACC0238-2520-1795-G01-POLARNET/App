package pe.edu.upc.polarnet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.polarnet.features.client.home.data.local.dao.EquipmentDao
import pe.edu.upc.polarnet.features.client.home.data.local.models.EquipmentEntity
import pe.edu.upc.polarnet.features.client.equipments.data.local.dao.ClientEquipmentDao
import pe.edu.upc.polarnet.features.client.equipments.data.local.models.ClientEquipmentEntity

@Database(
    entities = [EquipmentEntity::class, ClientEquipmentEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
    abstract fun clientEquipmentDao(): ClientEquipmentDao
}
