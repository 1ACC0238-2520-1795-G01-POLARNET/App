package pe.edu.upc.polarnet.features.provider.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors
import pe.edu.upc.polarnet.shared.models.ServiceRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceRequestDetailScreen(
    serviceRequest: ServiceRequest,
    onNavigateBack: () -> Unit,
    onAccept: (Long) -> Unit,
    onReject: (Long) -> Unit,
    isLoading: Boolean = false
) {
    val colors = MaterialTheme.polarNetColors
    var showAcceptDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    val isPending = serviceRequest.status.lowercase() == "pending"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Solicitud") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        },
        bottomBar = {
            if (isPending && !isLoading) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón Rechazar
                        OutlinedButton(
                            onClick = { showRejectDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = colors.critical.color
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                colors.critical.color
                            )
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Rechazar")
                        }

                        // Botón Aceptar
                        Button(
                            onClick = { showAcceptDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.success.color,
                                contentColor = colors.success.onColor
                            )
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Aceptar")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado Card
            StatusCard(serviceRequest)

            // Información del Cliente
            ClientInfoCard(serviceRequest)

            // Información del Equipo
            EquipmentInfoCard(serviceRequest)

            // Detalles de la Solicitud
            RequestDetailsCard(serviceRequest)

            // Información Adicional
            AdditionalInfoCard(serviceRequest)
        }

        // Diálogo de Confirmación - Aceptar
        if (showAcceptDialog) {
            AlertDialog(
                onDismissRequest = { showAcceptDialog = false },
                icon = {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = colors.success.color,
                        modifier = Modifier.size(32.dp)
                    )
                },
                title = {
                    Text(
                        "¿Aceptar Solicitud?",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text("La solicitud será marcada como completada y el equipo quedará asignado al cliente.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onAccept(serviceRequest.id)
                            showAcceptDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.success.color
                        )
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAcceptDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Diálogo de Confirmación - Rechazar
        if (showRejectDialog) {
            AlertDialog(
                onDismissRequest = { showRejectDialog = false },
                icon = {
                    Icon(
                        Icons.Default.Cancel,
                        contentDescription = null,
                        tint = colors.critical.color,
                        modifier = Modifier.size(32.dp)
                    )
                },
                title = {
                    Text(
                        "¿Rechazar Solicitud?",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text("La solicitud será eliminada permanentemente. Esta acción no se puede deshacer.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onReject(serviceRequest.id)
                            showRejectDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.critical.color
                        )
                    ) {
                        Text("Rechazar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showRejectDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Loading Overlay
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun StatusCard(serviceRequest: ServiceRequest) {
    val colors = MaterialTheme.polarNetColors
    val (statusColor, statusText, statusIcon) = when (serviceRequest.status.lowercase()) {
        "completed" -> Triple(colors.success, "Completada", Icons.Default.CheckCircle)
        "in_progress" -> Triple(colors.info, "En Progreso", Icons.Default.Construction)
        "pending" -> Triple(colors.warning, "Pendiente", Icons.Default.Schedule)
        "cancelled" -> Triple(colors.critical, "Cancelada", Icons.Default.Cancel)
        else -> Triple(colors.warning, serviceRequest.status, Icons.Default.Info)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = statusColor.colorContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Estado de la Solicitud",
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor.onColorContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = statusText.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = statusColor.onColorContainer
                )
            }
            Icon(
                statusIcon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = statusColor.onColorContainer
            )
        }
    }
}

@Composable
private fun ClientInfoCard(serviceRequest: ServiceRequest) {
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
                text = "Información del Cliente",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            serviceRequest.client?.let { client ->
                InfoRow(
                    icon = Icons.Default.Person,
                    label = "Nombre",
                    value = client.fullName
                )
                InfoRow(
                    icon = Icons.Default.Business,
                    label = "Empresa",
                    value = client.companyName ?: "N/A"
                )
                InfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = client.email
                )
                InfoRow(
                    icon = Icons.Default.Phone,
                    label = "Teléfono",
                    value = client.phone ?: "N/A"
                )
                InfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "Ubicación",
                    value = client.location ?: "N/A"
                )
            } ?: run {
                Text(
                    text = "Información no disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EquipmentInfoCard(serviceRequest: ServiceRequest) {
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
                text = "Equipo Solicitado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            serviceRequest.equipment?.let { equipment ->
                InfoRow(
                    icon = Icons.Default.Inventory,
                    label = "Nombre",
                    value = equipment.name
                )
                InfoRow(
                    icon = Icons.Default.Category,
                    label = "Categoría",
                    value = equipment.category
                )
                equipment.brand?.let {
                    InfoRow(
                        icon = Icons.Default.Label,
                        label = "Marca",
                        value = it
                    )
                }
                equipment.model?.let {
                    InfoRow(
                        icon = Icons.Default.Info,
                        label = "Modelo",
                        value = it
                    )
                }
            } ?: run {
                Text(
                    text = "Información del equipo no disponible",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun RequestDetailsCard(serviceRequest: ServiceRequest) {
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Detalles de la Solicitud",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.3f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha Inicio:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = serviceRequest.startDate ?: "N/A",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha Fin:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = serviceRequest.endDate ?: "N/A",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.3f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Precio Total:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "S/ %.2f".format(serviceRequest.totalPrice),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
private fun AdditionalInfoCard(serviceRequest: ServiceRequest) {
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
                text = "Información Adicional",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            serviceRequest.description?.let {
                InfoRow(
                    icon = Icons.Default.Description,
                    label = "Descripción",
                    value = it
                )
            }

            serviceRequest.notes?.let {
                InfoRow(
                    icon = Icons.Default.Notes,
                    label = "Notas",
                    value = it
                )
            }

            serviceRequest.createdAt?.let {
                InfoRow(
                    icon = Icons.Default.Schedule,
                    label = "Fecha de Creación",
                    value = it
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
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

