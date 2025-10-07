package pe.edu.upc.polarnet.features.home.domain.repositories

import pe.edu.upc.polarnet.shared.models.Equipment

interface EquipmentRepository {

    suspend fun getAllEquipments(): List<Equipment>

    suspend fun getEquipmentById(id: Long): Equipment?

    suspend fun insert(equipment: Equipment)

    suspend fun delete(equipment: Equipment)
}
