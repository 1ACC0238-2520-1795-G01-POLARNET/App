package pe.edu.upc.polarnet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upc.polarnet.features.client.home.data.local.dao.EquipmentDao
import pe.edu.upc.polarnet.features.client.home.data.local.models.EquipmentEntity
import pe.edu.upc.polarnet.features.client.equipments.data.local.dao.ClientEquipmentDao
import pe.edu.upc.polarnet.features.client.equipments.data.local.models.ClientEquipmentEntity
import pe.edu.upc.polarnet.features.client.services.data.local.dao.ServiceRequestDao
import pe.edu.upc.polarnet.features.client.services.data.local.models.ServiceRequestEntity

@Database(
    entities = [
        EquipmentEntity::class,
        ClientEquipmentEntity::class,
        pe.edu.upc.polarnet.features.client.services.data.local.models.ServiceRequestEntity::class,
        pe.edu.upc.polarnet.features.provider.home.data.local.models.ServiceRequestEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
    abstract fun clientEquipmentDao(): ClientEquipmentDao
    abstract fun serviceRequestDao(): pe.edu.upc.polarnet.features.client.services.data.local.dao.ServiceRequestDao
    abstract fun providerHomeServiceRequestDao(): pe.edu.upc.polarnet.features.provider.home.data.local.dao.ServiceRequestDao

}
