package pe.edu.upc.polarnet.core.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object MainCliente : Route("main_cliente")
    object MainProveedor : Route("main_proveedor")
    object ServiceRequests : Route("service_requests")

    object EquipmentDetail : Route("equipment_detail") {
        const val argument = "equipmentId"
        val routeWithArgument = "$route/{$argument}"
    }

    // 👇 Nueva ruta para detalle de solicitud de servicio
    object ServiceRequestDetail : Route("service_request_detail") {
        const val argument = "serviceRequestId"
        val routeWithArgument = "$route/{$argument}"
    }
}