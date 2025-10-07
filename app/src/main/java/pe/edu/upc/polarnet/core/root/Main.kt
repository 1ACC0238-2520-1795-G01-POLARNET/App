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
import pe.edu.upc.polarnet.features.client.home.presentation.home.Home
import pe.edu.upc.polarnet.features.client.equipments.presentation.ClientEquipmentsScreen

@Composable
fun Main(
    onTapEquipmentCard: (Long) -> Unit,
    clientId: Long = 2L // ⚠️ de prueba, reemplázalo con el ID real del usuario logueado
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
                0 -> Home { id -> onTapEquipmentCard(id) }

                // 🔹 Nueva pantalla: Mis Equipos del cliente
                1 -> ClientEquipmentsScreen(
                    clientId = clientId,
                    onTapEquipmentCard = onTapEquipmentCard
                )

                2 -> Text("Servicios (Próximamente...)")
                3 -> Text("Perfil del usuario (Próximamente...)")
            }
        }
    }
}

data class NavigationItem(val icon: ImageVector, val label: String)
