package pe.edu.upc.polarnet.features.client.home.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.polarnet.R
import pe.edu.upc.polarnet.core.ui.components.RoundedIcon
import pe.edu.upc.polarnet.core.ui.components.WidthSpacer
import pe.edu.upc.polarnet.features.auth.presentation.login.LoginViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
    onTapEquipmentCard: (Long) -> Unit,
    loginViewModel: LoginViewModel // ✅ Recibido desde Main
) {
    val loggedUser = loginViewModel.loggedUser.collectAsState().value

    val categories = listOf("All", "Freezers", "Refrigerators", "Showcases", "CSR")
    val selectedCategory = remember { mutableStateOf("All") }
    val equipments by viewModel.equipments.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔹 Header con saludo dinámico
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            WidthSpacer()
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hola, ${loggedUser?.fullName ?: "Usuario"} ",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "¡Bienvenido a PolarNet!",
                    fontWeight = FontWeight.SemiBold
                )
            }
            WidthSpacer()
            RoundedIcon(Icons.Outlined.Notifications)
            WidthSpacer()
            RoundedIcon(Icons.Outlined.ShoppingCart)
        }

        // 🔹 Campo de búsqueda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text(stringResource(R.string.placeholder_search)) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            )
            WidthSpacer()
            RoundedIcon(Icons.Outlined.FilterList)
        }

        // 🔹 Categorías
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.label_categories),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {}) { Text(stringResource(R.string.button_see_all)) }
        }

        LazyRow {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory.value == category,
                    onClick = { selectedCategory.value = category },
                    label = { Text(category) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // 🔹 Banner promocional
        Box(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.inversePrimary
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Get your special sale up to 40%",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    ElevatedButton(onClick = {}) { Text("Shop now") }
                }
                Image(
                    painterResource(R.drawable.banner),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }
        }

        // 🔹 Equipos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.label_equipments),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {}) { Text(stringResource(R.string.button_see_all)) }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(equipments) { equipment ->
                EquipmentCard(equipment) {
                    Log.d("Home", equipment.id.toString())
                    onTapEquipmentCard(equipment.id)
                }
            }
        }
    }
}
