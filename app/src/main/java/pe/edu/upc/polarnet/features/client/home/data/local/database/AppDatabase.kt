package pe.edu.upc.polarnet.features.client.home.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.polarnet.features.client.home.data.local.dao.EquipmentDao
import pe.edu.upc.polarnet.features.client.home.data.local.models.EquipmentEntity

@Database(entities = [EquipmentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
}
