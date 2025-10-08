package pe.edu.upc.polarnet.features.provider.addequipment.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEquipmentScreen(
    providerId: Long,
    viewModel: AddEquipmentViewModel = hiltViewModel(),
    onEquipmentAdded: () -> Unit = {}
) {
    val name = viewModel.name.collectAsState().value
    val brand = viewModel.brand.collectAsState().value
    val model = viewModel.model.collectAsState().value
    val category = viewModel.category.collectAsState().value
    val description = viewModel.description.collectAsState().value
    val thumbnail = viewModel.thumbnail.collectAsState().value
    val pricePerMonth = viewModel.pricePerMonth.collectAsState().value
    val purchasePrice = viewModel.purchasePrice.collectAsState().value
    val location = viewModel.location.collectAsState().value
    val available = viewModel.available.collectAsState().value

    val isLoading = viewModel.isLoading.collectAsState().value
    val successMessage = viewModel.successMessage.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    // Mostrar Snackbar para mensajes
    val snackbarHostState = androidx.compose.material3.SnackbarHostState()

    LaunchedEffect(successMessage) {
        successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Equipo", fontWeight = FontWeight.Bold) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nombre del equipo (obligatorio)
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Nombre del equipo *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Marca
            OutlinedTextField(
                value = brand,
                onValueChange = { viewModel.updateBrand(it) },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Modelo
            OutlinedTextField(
                value = model,
                onValueChange = { viewModel.updateModel(it) },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Categoría (Dropdown)
            var expandedCategory = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedCategory.value,
                onExpandedChange = { expandedCategory.value = !expandedCategory.value }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory.value) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    enabled = !isLoading
                )

                ExposedDropdownMenu(
                    expanded = expandedCategory.value,
                    onDismissRequest = { expandedCategory.value = false }
                ) {
                    viewModel.categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                viewModel.updateCategory(cat)
                                expandedCategory.value = false
                            }
                        )
                    }
                }
            }

            // Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5,
                enabled = !isLoading
            )

            // URL de imagen
            OutlinedTextField(
                value = thumbnail,
                onValueChange = { viewModel.updateThumbnail(it) },
                label = { Text("URL de imagen") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Precio por mes
            OutlinedTextField(
                value = pricePerMonth,
                onValueChange = { viewModel.updatePricePerMonth(it) },
                label = { Text("Precio por mes (S/) *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                enabled = !isLoading
            )

            // Precio de compra
            OutlinedTextField(
                value = purchasePrice,
                onValueChange = { viewModel.updatePurchasePrice(it) },
                label = { Text("Precio de compra (S/) *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                enabled = !isLoading
            )

            // Ubicación
            OutlinedTextField(
                value = location,
                onValueChange = { viewModel.updateLocation(it) },
                label = { Text("Ubicación") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            // Disponibilidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = available,
                    onCheckedChange = { viewModel.updateAvailable(it) },
                    enabled = !isLoading
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Disponible para alquiler",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón guardar
            Button(
                onClick = { viewModel.addEquipment(providerId, onEquipmentAdded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar Equipo", fontSize = 16.sp)
                }
            }

            // Nota
            Text(
                text = "* Campos obligatorios",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}