package pe.edu.upc.polarnet.shared.models

data class ClientEquipment(
    val id: Long,
    val clientId: Long,
    val equipmentId: Long,
    val ownershipType: String, // "rented" o "owned"
    val startDate: String,
    val endDate: String?,
    val status: String, // "active", "returned", "pending"
    val notes: String?,
    // Relación con Equipment
    val equipment: Equipment?
)