package pe.edu.upc.polarnet.features.client.equipments.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.client.equipments.data.local.dao.ClientEquipmentDao
import pe.edu.upc.polarnet.features.client.equipments.data.local.models.ClientEquipmentEntity
import pe.edu.upc.polarnet.features.client.equipments.data.remote.models.ClientEquipmentDto
import pe.edu.upc.polarnet.features.client.equipments.data.remote.services.ClientEquipmentService
import pe.edu.upc.polarnet.features.client.equipments.domain.repositories.ClientEquipmentRepository
import pe.edu.upc.polarnet.shared.models.ClientEquipment
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

class ClientEquipmentRepositoryImpl @Inject constructor(
    private val service: ClientEquipmentService,
    private val dao: ClientEquipmentDao
) : ClientEquipmentRepository {

    //  Obtener todos los equipos de un cliente
    override suspend fun getClientEquipments(clientId: Long): List<ClientEquipment> = withContext(Dispatchers.IO) {
        try {
            val response = service.getClientEquipmentsByClientId("eq.$clientId")

            if (response.isSuccessful) {
                val clientEquipments = response.body()?.map { dto ->
                    dto.toDomain()
                } ?: emptyList()

                Log.d("ClientEquipmentRepo", "Equipos cliente obtenidos: ${clientEquipments.size}")

                // Guardar localmente
                clientEquipments.forEach { ce ->
                    dao.insert(
                        ClientEquipmentEntity(
                            id = ce.id,
                            clientId = ce.clientId,
                            equipmentId = ce.equipmentId,
                            ownershipType = ce.ownershipType,
                            startDate = ce.startDate,
                            endDate = ce.endDate,
                            status = ce.status,
                            notes = ce.notes
                        )
                    )
                }

                return@withContext clientEquipments
            } else {
                Log.e("ClientEquipmentRepo", "Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("ClientEquipmentRepo", "Excepción: ${e.message}", e)
        }

        // Si falla la red, cargar desde Room
        return@withContext dao.fetchByClient(clientId).map {
            ClientEquipment(
                id = it.id,
                clientId = it.clientId,
                equipmentId = it.equipmentId,
                ownershipType = it.ownershipType,
                startDate = it.startDate,
                endDate = it.endDate,
                status = it.status,
                notes = it.notes,
                equipment = null
            )
        }
    }

    // 🔹 Obtener un equipo cliente específico
    override suspend fun getClientEquipmentById(id: Long): ClientEquipment? = withContext(Dispatchers.IO) {
        try {
            val response = service.getClientEquipmentById("eq.$id")

            if (response.isSuccessful) {
                val dto = response.body()?.firstOrNull()
                return@withContext dto?.toDomain()
            }
        } catch (e: Exception) {
            Log.e("ClientEquipmentRepo", "Excepción al obtener equipo cliente: ${e.message}", e)
        }

        // Si falla, usar local
        dao.fetchById(id)?.let {
            return@withContext ClientEquipment(
                id = it.id,
                clientId = it.clientId,
                equipmentId = it.equipmentId,
                ownershipType = it.ownershipType,
                startDate = it.startDate,
                endDate = it.endDate,
                status = it.status,
                notes = it.notes,
                equipment = null
            )
        }
    }

    // 🔹 Guardar en Room
    override suspend fun insert(clientEquipment: ClientEquipment) = withContext(Dispatchers.IO) {
        dao.insert(
            ClientEquipmentEntity(
                id = clientEquipment.id,
                clientId = clientEquipment.clientId,
                equipmentId = clientEquipment.equipmentId,
                ownershipType = clientEquipment.ownershipType,
                startDate = clientEquipment.startDate,
                endDate = clientEquipment.endDate,
                status = clientEquipment.status,
                notes = clientEquipment.notes
            )
        )
    }

    // 🔹 Eliminar de Room
    override suspend fun delete(clientEquipment: ClientEquipment) = withContext(Dispatchers.IO) {
        dao.delete(
            ClientEquipmentEntity(
                id = clientEquipment.id,
                clientId = clientEquipment.clientId,
                equipmentId = clientEquipment.equipmentId,
                ownershipType = clientEquipment.ownershipType,
                startDate = clientEquipment.startDate,
                endDate = clientEquipment.endDate,
                status = clientEquipment.status,
                notes = clientEquipment.notes
            )
        )
    }
}

/**
 *  Mapper de DTO a Dominio
 */
private fun ClientEquipmentDto.toDomain(): ClientEquipment = ClientEquipment(
    id = id,
    clientId = clientId,
    equipmentId = equipmentId,
    ownershipType = ownershipType,
    startDate = startDate,
    endDate = endDate,
    status = status,
    notes = notes,
    equipment = equipment?.let {
        Equipment(
            id = it.id,
            providerId = it.providerId,
            name = it.name,
            brand = it.brand,
            model = it.model,
            category = it.category,
            description = it.description,
            thumbnail = it.thumbnail,
            specifications = it.specifications,
            available = it.available,
            location = it.location,
            pricePerMonth = it.pricePerMonth,
            purchasePrice = it.purchasePrice,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }
)
