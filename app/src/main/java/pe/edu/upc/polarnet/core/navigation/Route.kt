package pe.edu.upc.polarnet.core.navigation

sealed class Route(val route: String) {

    object Login : Route("login")
    object MainCliente : Route("main_cliente")
    object MainProveedor : Route("main_proveedor")

    object EquipmentDetail : Route("equipment_detail") {
        const val routeWithArgument = "equipment_detail/{id}"
        const val argument = "id"
    }

    object ServiceRequests : Route("service_requests")
}
