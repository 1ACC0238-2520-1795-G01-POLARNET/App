package pe.edu.upc.polarnet.features.provider.add.domain.repositories

import pe.edu.upc.polarnet.shared.models.Equipment

interface AddEquipmentRepository {
    suspend fun addEquipment(equipment: Equipment): Boolean
}