package pe.edu.upc.polarnet.features.client.home.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.upc.polarnet.features.client.home.data.local.models.EquipmentEntity

@Dao
interface EquipmentDao {

    @Insert
    suspend fun insert(vararg entity: EquipmentEntity)

    @Delete
    suspend fun delete(vararg entity: EquipmentEntity)

    @Query("SELECT * FROM equipments WHERE id = :id")
    suspend fun fetchById(id: Int): List<EquipmentEntity>
}
