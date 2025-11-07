package pe.edu.upc.polarnet.features.client.services.presentation.serviceRequest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors
import pe.edu.upc.polarnet.shared.models.ServiceRequest

@Composable
fun ServiceRequestCard(request: ServiceRequest) {
    val colors = MaterialTheme.polarNetColors

    val (statusColor, statusText, statusIcon) = when (request.status.lowercase()) {
        "completed" -> Triple(
            colors.success,
            "Completado",
            Icons.Default.CheckCircle
        )
        "in_progress" -> Triple(
            colors.info,
            "En Proceso",
            Icons.Default.Construction
        )
        "pending" -> Triple(
            colors.warning,
            "Pendiente",
            Icons.Default.Schedule
        )
        else -> Triple(
            MaterialTheme.colorScheme.run {
                pe.edu.upc.polarnet.core.ui.theme.ColorFamily(
                    color = onSurfaceVariant,
                    onColor = surface,
                    colorContainer = surfaceVariant,
                    onColorContainer = onSurfaceVariant
                )
            },
            request.status,
            Icons.Default.Info
        )
    }

    val requestTypeText = when (request.requestType.lowercase()) {
        "maintenance" -> "Mantenimiento"
        "repair" -> "Reparación"
        "installation" -> "Instalación"
        "inspection" -> "Inspección"
        else -> request.requestType
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Tipo y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = requestTypeText.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Solicitud #${request.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = statusColor.colorContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            statusIcon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = statusColor.onColorContainer
                        )
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = statusColor.onColorContainer
                        )
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Detalles en grid
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DetailRow(
                    icon = Icons.Default.Devices,
                    label = "Equipo",
                    value = "ID: ${request.equipmentId}"
                )

                DetailRow(
                    icon = Icons.Default.AttachMoney,
                    label = "Precio Total",
                    value = "S/ ${String.format("%.2f", request.totalPrice)}"
                )

                request.startDate?.let { startDate ->
                    DetailRow(
                        icon = Icons.Default.EventAvailable,
                        label = "Fecha Inicio",
                        value = startDate
                    )
                }

                request.endDate?.let { endDate ->
                    DetailRow(
                        icon = Icons.Default.Event,
                        label = "Fecha Fin",
                        value = endDate
                    )
                }
            }

            // Notas (si existen)
            if (!request.notes.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.StickyNote2,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Column {
                            Text(
                                text = "Notas",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = request.notes,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // Acción según estado
            if (request.status.lowercase() == "completed") {
                FilledTonalButton(
                    onClick = { /* Ver detalles */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver Detalles")
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
                style = MaterialTheme.typography.labelSmall,
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