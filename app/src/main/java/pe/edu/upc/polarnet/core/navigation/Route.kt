package pe.edu.upc.polarnet.core.navigation

sealed class Route(val route: String){
    object Login: Route("login")
    object MainCliente: Route("main_cliente")
    object MainProveedor: Route("main_proveedor")
    object ProductDetail: Route("product_detail") {
        const val routeWithArgument = "product_detail/{id}"
        const val argument = "id"
    }
}