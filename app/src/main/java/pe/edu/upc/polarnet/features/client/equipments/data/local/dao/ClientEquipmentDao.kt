package pe.edu.upc.polarnet.features.client.equipments.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.upc.polarnet.features.client.equipments.data.local.models.ClientEquipmentEntity

@Dao
interface ClientEquipmentDao {

    @Insert
    suspend fun insert(vararg entity: ClientEquipmentEntity)

    @Delete
    suspend fun delete(vararg entity: ClientEquipmentEntity)

    @Query("SELECT * FROM client_equipments WHERE clientId = :clientId")
    suspend fun fetchByClient(clientId: Long): List<ClientEquipmentEntity>

    @Query("SELECT * FROM client_equipments WHERE id = :id")
    suspend fun fetchById(id: Long): ClientEquipmentEntity?
}
