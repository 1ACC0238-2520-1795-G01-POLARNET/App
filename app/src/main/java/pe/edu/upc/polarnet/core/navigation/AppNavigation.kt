package pe.edu.upc.polarnet.core.navigation

import androidx.compose.runtime.Composable
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
import pe.edu.upc.polarnet.features.auth.presentation.di.PresentationModule.getLoginViewModel
import pe.edu.upc.polarnet.features.auth.presentation.login.Login
import pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail.EquipmentDetail
import pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail.EquipmentDetailViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel = getLoginViewModel()

    NavHost(navController, startDestination = Route.Login.route) {
        // Login
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

        // Main Cliente
        // Main Cliente
        composable(Route.MainCliente.route) {
            Main(
                onTapEquipmentCard = { equipmentId: Long ->
                    navController.navigate("${Route.EquipmentDetail.route}/$equipmentId")
                }
            )
        }


        // Main Proveedor
        composable(Route.MainProveedor.route) {
            MainProveedor { equipmentId: Long ->
                navController.navigate("${Route.EquipmentDetail.route}/$equipmentId")
            }
        }

        // Equipment Detail
        composable(
            route = Route.EquipmentDetail.routeWithArgument,
            arguments = listOf(navArgument(Route.EquipmentDetail.argument) {
                type = NavType.LongType // <-- Cambiado a Long
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { arguments ->
                val equipmentId = arguments.getLong(Route.EquipmentDetail.argument) // <-- getLong
                val equipmentDetailViewModel: EquipmentDetailViewModel = hiltViewModel()

                equipmentDetailViewModel.getEquipmentById(equipmentId) // <-- recibe Long
                EquipmentDetail(equipmentDetailViewModel)
            }
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
