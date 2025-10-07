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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.profile.ProfileScreen
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel
import pe.edu.upc.polarnet.features.client.home.presentation.home.Home

@Composable
fun MainProveedor(
    providerId: Long,
    loginViewModel: LoginViewModel,
    onTapEquipmentCard: (Long) -> Unit
) {
    // 👇 Recuperamos el usuario logueado
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

                0 -> Home(
                    onTapEquipmentCard = onTapEquipmentCard,
                    loginViewModel = loginViewModel
                )

                1 -> Text(
                    text = "Inventario del proveedor ${loggedUser?.fullName ?: "desconocido"} (ID: $providerId)",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.SemiBold
                )

                2 -> Text(
                    text = "Agregar nuevo producto",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.SemiBold
                )

                3 -> ProfileScreen(loginViewModel = loginViewModel)
            }
        }
    }
}
