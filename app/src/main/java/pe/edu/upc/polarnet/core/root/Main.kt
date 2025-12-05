package pe.edu.upc.polarnet.core.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.polarnet.core.profile.ProfileScreen
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel
import pe.edu.upc.polarnet.features.client.equipments.presentation.ClientEquipmentsScreen
import pe.edu.upc.polarnet.features.client.home.presentation.home.Home
import pe.edu.upc.polarnet.features.client.services.presentation.ServiceRequestViewModel
import pe.edu.upc.polarnet.features.client.services.presentation.serviceRequest.ServiceRequestScreen

@Composable
fun Main(
    clientId: Long,
    onTapEquipmentCard: (Long) -> Unit,
    loginViewModel: LoginViewModel,
    onNavigateToNotifications: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val navigationItems = listOf(
        NavigationItem(Icons.Default.Home, "Inicio"),
        NavigationItem(Icons.Default.Build, "Mis Equipos"),
        NavigationItem(Icons.Default.Settings, "Servicios"),
        NavigationItem(Icons.Default.Person, "Perfil")
    )

    val selectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex.intValue,
                        onClick = { selectedIndex.intValue = index },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedIndex.intValue) {
                0 -> Home(
                    onTapEquipmentCard = onTapEquipmentCard,
                    loginViewModel = loginViewModel,
                    onNavigateToNotifications = onNavigateToNotifications
                )

                1 -> ClientEquipmentsScreen(
                    clientId = clientId,
                    onTapEquipmentCard = onTapEquipmentCard
                )

                2 -> {
                    val serviceViewModel: ServiceRequestViewModel = hiltViewModel()
                    ServiceRequestScreen(clientId = clientId, viewModel = serviceViewModel)
                }

                3 -> ProfileScreen(
                    loginViewModel = loginViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}

data class NavigationItem(val icon: ImageVector, val label: String)
