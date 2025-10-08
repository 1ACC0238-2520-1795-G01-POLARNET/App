package pe.edu.upc.polarnet.features.provider.add.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.provider.add.data.remote.models.CreateEquipmentDto
import pe.edu.upc.polarnet.features.provider.add.data.remote.services.AddEquipmentService
import pe.edu.upc.polarnet.features.provider.add.domain.repositories.AddEquipmentRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

class AddEquipmentRepositoryImpl @Inject constructor(
    private val service: AddEquipmentService
) : AddEquipmentRepository {

    override suspend fun addEquipment(equipment: Equipment): Boolean = withContext(Dispatchers.IO) {
        try {
            val dto = CreateEquipmentDto(
                providerId = equipment.providerId,
                name = equipment.name,
                brand = equipment.brand,
                model = equipment.model,
                category = equipment.category,
                description = equipment.description,
                thumbnail = equipment.thumbnail,
                specifications = equipment.specifications,
                available = equipment.available,
                location = equipment.location,
                pricePerMonth = equipment.pricePerMonth,
                purchasePrice = equipment.purchasePrice
            )

            val response = service.createEquipment(dto)

            if (response.isSuccessful) {
                Log.d("AddEquipmentRepo", "Equipo creado exitosamente")
                return@withContext true
            } else {
                Log.e("AddEquipmentRepo", "Error: ${response.code()} - ${response.message()}")
                Log.e("AddEquipmentRepo", "Body: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("AddEquipmentRepo", "Exception: ${e.message}", e)
        }

        return@withContext false
    }
}