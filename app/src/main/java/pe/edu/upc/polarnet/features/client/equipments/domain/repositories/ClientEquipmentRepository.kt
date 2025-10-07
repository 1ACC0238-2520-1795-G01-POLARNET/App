package pe.edu.upc.polarnet.features.client.equipments.domain.repositories

import pe.edu.upc.polarnet.shared.models.ClientEquipment

interface ClientEquipmentRepository {

    suspend fun getClientEquipments(clientId: Long): List<ClientEquipment>

    suspend fun getClientEquipmentById(id: Long): ClientEquipment?

    suspend fun insert(clientEquipment: ClientEquipment)

    suspend fun delete(clientEquipment: ClientEquipment)
}
