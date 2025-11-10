package pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors

@Composable
fun EquipmentDetail(
    viewModel: EquipmentDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    clientId: Long
) {
    val equipment by viewModel.equipment.collectAsState()
    var rentalMonths by remember { mutableIntStateOf(1) }
    var showRentalDialog by remember { mutableStateOf(false) }
    val colors = MaterialTheme.polarNetColors

    val isLoading by viewModel.isLoading.collectAsState()
    val rentalSuccess by viewModel.rentalSuccess.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Mostrar mensaje de éxito
    LaunchedEffect(rentalSuccess) {
        if (rentalSuccess) {
            android.util.Log.d("EquipmentDetail", "✅ Renta creada exitosamente")
        }
    }

    // Mostrar error si existe
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            android.util.Log.e("EquipmentDetail", "❌ Error: $it")
        }
    }

    equipment?.let { equip ->
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showRentalDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rentar Equipo",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->

            // Diálogo de confirmación de renta
            if (showRentalDialog) {
                RentalDialog(
                    equipmentName = equip.name,
                    pricePerMonth = equip.pricePerMonth,
                    rentalMonths = rentalMonths,
                    onRentalMonthsChange = { rentalMonths = it },
                    onConfirm = {
                        android.util.Log.d("EquipmentDetail", "====================================")
                        android.util.Log.d("EquipmentDetail", "🎯 CONFIRMANDO RENTA")
                        android.util.Log.d("EquipmentDetail", "====================================")
                        android.util.Log.d("EquipmentDetail", "🔑 ClientId recibido: $clientId")
                        android.util.Log.d("EquipmentDetail", "📦 EquipmentId: ${equip.id}")
                        android.util.Log.d("EquipmentDetail", "📋 Equipo: ${equip.name}")

                        viewModel.createRentalRequest(
                            clientId = clientId,
                            equipmentId = equip.id,
                            rentalMonths = rentalMonths,
                            pricePerMonth = equip.pricePerMonth
                        )
                        showRentalDialog = false
                    },
                    onDismiss = { showRentalDialog = false },
                    isLoading = isLoading
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            ) {
                // Imagen del equipo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = equip.thumbnail,
                        contentDescription = equip.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay con gradiente
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                        MaterialTheme.colorScheme.background.copy(alpha = 0.3f)
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                )
                            )
                    )

                    // Botón de volver
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Nombre y precio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = equip.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = colors.success.color
                                )
                                Text(
                                    text = "Disponible",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.success.color,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "Precio/mes",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "S/ %.2f".format(equip.pricePerMonth),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // Sección de meses de renta
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Duración de Renta",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "S/ ${String.format(java.util.Locale.getDefault(), "%.2f", equip.pricePerMonth)}/mes",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Botón decrementar
                                FilledIconButton(
                                    onClick = { if (rentalMonths > 1) rentalMonths-- },
                                    modifier = Modifier.size(40.dp),
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Remove,
                                        contentDescription = "Disminuir",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                // Meses
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.width(80.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = rentalMonths.toString(),
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = if (rentalMonths == 1) "mes" else "meses",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Botón incrementar
                                FilledIconButton(
                                    onClick = { rentalMonths++ },
                                    modifier = Modifier.size(40.dp),
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Aumentar",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Información del producto
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Detalles del Producto",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            DetailRow(
                                icon = Icons.Default.Category,
                                label = "Categoría",
                                value = equip.category
                            )

                            DetailRow(
                                icon = Icons.Default.Inventory,
                                label = "Stock",
                                value = if (equip.available) "Disponible" else "No disponible"
                            )

                            DetailRow(
                                icon = Icons.Default.LocalShipping,
                                label = "Ubicación",
                                value = equip.location ?: "Lima, Perú"
                            )

                            DetailRow(
                                icon = Icons.Default.Verified,
                                label = "Marca",
                                value = equip.brand ?: "N/A"
                            )
                        }
                    }

                    // Resumen de renta
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Resumen de Renta",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Precio por mes",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    text = "S/ %.2f".format(equip.pricePerMonth),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Duración",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    text = "$rentalMonths ${if (rentalMonths == 1) "mes" else "meses"}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.3f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column {
                                    Text(
                                        text = "Total a Pagar",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                    Text(
                                        text = "Por $rentalMonths ${if (rentalMonths == 1) "mes" else "meses"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                                    )
                                }
                                Text(
                                    text = "S/ %.2f".format(equip.pricePerMonth * rentalMonths),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }
                    }

                    // Espacio para el FAB
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun RentalDialog(
    equipmentName: String,
    pricePerMonth: Double,
    rentalMonths: Int,
    onRentalMonthsChange: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        icon = {
            Icon(
                Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Confirmar Renta",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = equipmentName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                HorizontalDivider()

                // Selector de meses
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Duración:",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { if (rentalMonths > 1) onRentalMonthsChange(rentalMonths - 1) },
                            enabled = !isLoading && rentalMonths > 1
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Disminuir")
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            modifier = Modifier.widthIn(min = 60.dp)
                        ) {
                            Text(
                                text = "$rentalMonths ${if (rentalMonths == 1) "mes" else "meses"}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }

                        IconButton(
                            onClick = { onRentalMonthsChange(rentalMonths + 1) },
                            enabled = !isLoading
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Aumentar")
                        }
                    }
                }

                // Resumen
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Precio/mes:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "S/ %.2f".format(pricePerMonth),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Total:",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "S/ %.2f".format(pricePerMonth * rentalMonths),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isLoading) "Procesando..." else "Confirmar Renta")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Cancelar")
            }
        }
    )
}

