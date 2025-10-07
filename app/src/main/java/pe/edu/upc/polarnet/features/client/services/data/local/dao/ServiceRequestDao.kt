package pe.edu.upc.polarnet.features.client.services.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.upc.polarnet.features.client.services.data.local.models.ServiceRequestEntity

@Dao
interface ServiceRequestDao {

    @Insert
    suspend fun insert(vararg entity: ServiceRequestEntity)

    @Delete
    suspend fun delete(vararg entity: ServiceRequestEntity)

    @Query("SELECT * FROM service_requests WHERE clientId = :clientId")
    suspend fun getByClient(clientId: Long): List<ServiceRequestEntity>

    @Query("SELECT * FROM service_requests")
    suspend fun getAll(): List<ServiceRequestEntity>
}
