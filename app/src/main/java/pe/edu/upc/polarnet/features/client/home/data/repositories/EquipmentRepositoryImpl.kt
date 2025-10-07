package pe.edu.upc.polarnet.features.client.home.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.client.home.data.local.dao.EquipmentDao
import pe.edu.upc.polarnet.features.client.home.data.local.models.EquipmentEntity
import pe.edu.upc.polarnet.features.client.home.data.remote.services.EquipmentService
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val service: EquipmentService,
    private val dao: EquipmentDao
) : EquipmentRepository {

    override suspend fun getAllEquipments(): List<Equipment> = withContext(Dispatchers.IO) {
        try {
            val response = service.getAllEquipments()

            if (response.isSuccessful) {
                val equipments = response.body()?.map { dto ->
                    Equipment(
                        id = dto.id,
                        providerId = dto.providerId,
                        name = dto.name,
                        brand = dto.brand,
                        model = dto.model,
                        category = dto.category,
                        description = dto.description,
                        thumbnail = dto.thumbnail,
                        specifications = dto.specifications,
                        available = dto.available,
                        location = dto.location,
                        pricePerMonth = dto.pricePerMonth,
                        purchasePrice = dto.purchasePrice,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt
                    )
                } ?: emptyList()

                Log.d("EquipmentRepo", "Equipos obtenidos: ${equipments.size}")
                return@withContext equipments
            } else {
                Log.e("EquipmentRepo", "Error: ${response.code()} - ${response.message()}")
                Log.e("EquipmentRepo", "Body: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("EquipmentRepo", "Exception: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun getEquipmentById(id: Long): Equipment? = withContext(Dispatchers.IO) {
        try {
            val response = service.getEquipmentById("eq.$id")

            if (response.isSuccessful) {
                response.body()?.firstOrNull()?.let { dto ->
                    return@withContext Equipment(
                        id = dto.id,
                        providerId = dto.providerId,
                        name = dto.name,
                        brand = dto.brand,
                        model = dto.model,
                        category = dto.category,
                        description = dto.description,
                        thumbnail = dto.thumbnail,
                        specifications = dto.specifications,
                        available = dto.available,
                        location = dto.location,
                        pricePerMonth = dto.pricePerMonth,
                        purchasePrice = dto.purchasePrice,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("EquipmentRepo", "Exception getting equipment by id: ${e.message}", e)
        }

        return@withContext null
    }

    override suspend fun insert(equipment: Equipment) = withContext(Dispatchers.IO) {
        dao.insert(
            EquipmentEntity(
                id = equipment.id,
                name = equipment.name,
                category = equipment.category,
                description = equipment.description,
                thumbnail = equipment.thumbnail,
                price_per_month = equipment.pricePerMonth,
                purchase_price = equipment.purchasePrice,
                available = equipment.available,
                location = equipment.location
            )
        )
    }

    override suspend fun delete(equipment: Equipment) = withContext(Dispatchers.IO) {
        dao.delete(
            EquipmentEntity(
                id = equipment.id,
                name = equipment.name,
                category = equipment.category,
                description = equipment.description,
                thumbnail = equipment.thumbnail,
                price_per_month = equipment.pricePerMonth,
                purchase_price = equipment.purchasePrice,
                available = equipment.available,
                location = equipment.location
            )
        )
    }
}