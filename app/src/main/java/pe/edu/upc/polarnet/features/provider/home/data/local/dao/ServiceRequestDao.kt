package pe.edu.upc.polarnet.features.provider.home.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.polarnet.features.provider.home.data.local.models.ServiceRequestEntity

@Dao
interface ServiceRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entity: ServiceRequestEntity)

    @Delete
    suspend fun delete(vararg entity: ServiceRequestEntity)

    @Query("SELECT * FROM service_requests WHERE id = :id")
    suspend fun fetchById(id: Long): ServiceRequestEntity?

    @Query("SELECT * FROM service_requests ORDER BY createdAt DESC")
    suspend fun fetchAll(): List<ServiceRequestEntity>

    @Query("SELECT * FROM service_requests WHERE status = :status ORDER BY createdAt DESC")
    suspend fun fetchByStatus(status: String): List<ServiceRequestEntity>
}