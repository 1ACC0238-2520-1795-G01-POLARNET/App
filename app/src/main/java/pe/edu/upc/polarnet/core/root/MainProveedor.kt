package pe.edu.upc.polarnet.core.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import pe.edu.upc.polarnet.core.profile.ProfileScreen
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel
import pe.edu.upc.polarnet.features.provider.addequipment.presentation.AddEquipmentScreen
import pe.edu.upc.polarnet.features.provider.home.presentation.ProviderHomeScreen
import pe.edu.upc.polarnet.features.provider.inventory.presentation.ProviderInventoryScreen

@Composable
fun MainProveedor(
    providerId: Long,
    loginViewModel: LoginViewModel,
    onTapServiceRequest: (Long) -> Unit,
    onTapEquipment: (Long) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val loggedUser = loginViewModel.loggedUser.collectAsState().value

    val navigationItems = listOf(
        NavigationItem(Icons.Default.Home, "Inicio"),
        NavigationItem(Icons.Default.Inventory, "Inventario"),
        NavigationItem(Icons.Default.Add, "Agregar"),
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

                0 -> ProviderHomeScreen(
                    onTapServiceRequest = onTapServiceRequest
                )

                1 -> ProviderInventoryScreen(
                    providerId = providerId,
                    onTapEquipment = onTapEquipment
                )

                2 -> AddEquipmentScreen(
                    providerId = providerId,
                    onEquipmentAdded = {
                        // Volver a inventario después de agregar
                        selectedIndex.intValue = 1
                    }
                )

                3 -> ProfileScreen(
                    loginViewModel = loginViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}