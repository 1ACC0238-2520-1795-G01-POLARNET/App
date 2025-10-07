package pe.edu.upc.polarnet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.polarnet.core.root.Main
import pe.edu.upc.polarnet.core.root.MainProveedor
import pe.edu.upc.polarnet.core.ui.theme.PolarNetTheme
import pe.edu.upc.polarnet.features.auth.presentation.login.Login
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel
import pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail.EquipmentDetail
import pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail.EquipmentDetailViewModel
import pe.edu.upc.polarnet.features.client.services.presentation.serviceRequest.ServiceRequestScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val loginViewModel: LoginViewModel = hiltViewModel()

    val loggedUser = loginViewModel.loggedUser.collectAsState().value

    NavHost(navController, startDestination = Route.Login.route) {

        // 🔹 LOGIN SCREEN
        composable(Route.Login.route) {
            Login(
                viewModel = loginViewModel,
                onLoginCliente = {
                    navController.navigate(Route.MainCliente.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onLoginProveedor = {
                    navController.navigate(Route.MainProveedor.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // 🔹 MAIN CLIENTE
        composable(Route.MainCliente.route) {
            // 👇 Obtenemos el ID del usuario autenticado
            val clientId = loggedUser?.id ?: 0L

            Main(
                clientId = clientId,
                onTapEquipmentCard = { equipmentId: Long ->
                    navController.navigate("${Route.EquipmentDetail.route}/$equipmentId")
                }
            )
        }

        // 🔹 MAIN PROVEEDOR
        composable(Route.MainProveedor.route) {
            MainProveedor { equipmentId: Long ->
                navController.navigate("${Route.EquipmentDetail.route}/$equipmentId")
            }
        }

        // 🔹 DETALLE DE EQUIPO
        composable(
            route = Route.EquipmentDetail.routeWithArgument,
            arguments = listOf(
                navArgument(Route.EquipmentDetail.argument) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val equipmentId = navBackStackEntry.arguments?.getLong(Route.EquipmentDetail.argument)
            val equipmentDetailViewModel: EquipmentDetailViewModel = hiltViewModel()

            equipmentId?.let {
                equipmentDetailViewModel.getEquipmentById(it)
                EquipmentDetail(equipmentDetailViewModel)
            }
        }

        // 🔹 SERVICIOS DEL CLIENTE
        composable(Route.ServiceRequests.route) {
            val clientId = loggedUser?.id ?: 0L
            ServiceRequestScreen(clientId = clientId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    PolarNetTheme {
        AppNavigation()
    }
}
