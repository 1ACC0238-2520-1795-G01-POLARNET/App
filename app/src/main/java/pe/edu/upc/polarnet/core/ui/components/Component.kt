package pe.edu.upc.polarnet.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.core.ui.theme.ColorFamily
import pe.edu.upc.polarnet.core.ui.theme.getEquipmentStatusColor
import pe.edu.upc.polarnet.core.ui.theme.getTemperatureStatusColor
import pe.edu.upc.polarnet.core.ui.theme.polarNetColors

// ============================================
// STATUS BADGES - Indicadores de estado
// ============================================

/**
 * Badge de estado de temperatura con colores automáticos según el valor
 */
@Composable
fun TemperatureStatusBadge(
    temperature: Float,
    minTemp: Float,
    maxTemp: Float,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    val statusColor = getTemperatureStatusColor(temperature, minTemp, maxTemp)

    val icon = when {
        temperature < minTemp || temperature > maxTemp -> Icons.Default.Warning
        temperature < minTemp + (maxTemp - minTemp) * 0.1f ||
                temperature > maxTemp - (maxTemp - minTemp) * 0.1f -> Icons.Default.Info
        else -> Icons.Default.CheckCircle
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = statusColor.colorContainer,
        contentColor = statusColor.onColorContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (showIcon) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Badge simple con color personalizado
 */
@Composable
fun StatusBadge(
    text: String,
    colorFamily: ColorFamily,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = colorFamily.colorContainer,
        contentColor = colorFamily.onColorContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Indicador de punto de estado (usado en listas de equipos)
 */
@Composable
fun StatusDot(
    isOnline: Boolean,
    isActive: Boolean = false,
    inMaintenance: Boolean = false,
    modifier: Modifier = Modifier
) {
    val color = getEquipmentStatusColor(isOnline, isActive, inMaintenance)

    Box(
        modifier = modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}

// ============================================
// CARDS - Tarjetas especializadas
// ============================================

/**
 * Card de equipo de refrigeración con diseño profesional
 */
@Composable
fun EquipmentCard(
    equipmentName: String,
    location: String,
    temperature: Float,
    minTemp: Float,
    maxTemp: Float,
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header con nombre y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = equipmentName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                StatusDot(
                    isOnline = isOnline,
                    isActive = true,
                    modifier = Modifier.size(12.dp)
                )
            }

            // Temperatura
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Temperatura actual",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${temperature}°C",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                TemperatureStatusBadge(
                    temperature = temperature,
                    minTemp = minTemp,
                    maxTemp = maxTemp
                )
            }

            // Rango permitido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RangeChip(
                    label = "Min",
                    value = "${minTemp}°C",
                    modifier = Modifier.weight(1f)
                )
                RangeChip(
                    label = "Max",
                    value = "${maxTemp}°C",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Chip para mostrar rangos de temperatura
 */
@Composable
private fun RangeChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Card de alerta/notificación
 */
@Composable
fun AlertCard(
    title: String,
    message: String,
    severity: AlertSeverity,
    timestamp: String? = null,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null
) {
    val colors = MaterialTheme.polarNetColors
    val colorFamily = when (severity) {
        AlertSeverity.CRITICAL -> colors.critical
        AlertSeverity.WARNING -> colors.warning
        AlertSeverity.INFO -> colors.info
    }

    val icon = when (severity) {
        AlertSeverity.CRITICAL -> Icons.Default.Warning
        AlertSeverity.WARNING -> Icons.Default.Info
        AlertSeverity.INFO -> Icons.Default.Notifications
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorFamily.colorContainer
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = Brush.linearGradient(listOf(colorFamily.color, colorFamily.color))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorFamily.color,
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colorFamily.onColorContainer
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorFamily.onColorContainer.copy(alpha = 0.8f)
                )
                timestamp?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = colorFamily.onColorContainer.copy(alpha = 0.6f)
                    )
                }
            }

            onDismiss?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = colorFamily.onColorContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

enum class AlertSeverity {
    CRITICAL, WARNING, INFO
}

// ============================================
// STAT CARDS - Tarjetas de estadísticas
// ============================================

/**
 * Card compacta para mostrar estadísticas clave
 */
@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    trend: Trend? = null,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier,
        onClick = onClick ?: {},
        enabled = onClick != null,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(24.dp)
                )

                trend?.let {
                    TrendIndicator(trend = it)
                }
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * Indicador de tendencia (arriba/abajo)
 */
@Composable
fun TrendIndicator(
    trend: Trend,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.polarNetColors
    val (icon, color) = when (trend.direction) {
        TrendDirection.UP -> Icons.Default.KeyboardArrowUp to colors.success.color
        TrendDirection.DOWN -> Icons.Default.KeyboardArrowDown to colors.critical.color
        TrendDirection.STABLE -> Icons.Default.Remove to colors.info.color
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = trend.value,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

data class Trend(
    val direction: TrendDirection,
    val value: String
)

enum class TrendDirection {
    UP, DOWN, STABLE
}

// ============================================
// HEADERS - Encabezados de sección
// ============================================

/**
 * Header de sección con gradiente
 */
@Composable
fun GradientSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    val colors = MaterialTheme.polarNetColors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        colors.gradientStart,
                        colors.gradientMiddle,
                        colors.gradientEnd
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}