package pe.edu.upc.polarnet.features.client.home.data.remote.models

data class EquipmentsWrapperDto(
    val limit: Int,
    val equipments: List<EquipmentDto>,
    val skip: Int,
    val total: Int
)
