package pe.edu.upc.polarnet.features.provider.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.polarnet.shared.models.ServiceRequest

@Composable
fun ServiceRequestCard(
    serviceRequest: ServiceRequest,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header con tipo de solicitud y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getRequestTypeLabel(serviceRequest.requestType),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                StatusBadge(status = serviceRequest.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Información del cliente
            serviceRequest.client?.let { client ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Cliente: ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = client.companyName ?: client.fullName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Información del equipo
            serviceRequest.equipment?.let { equipment ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Equipo: ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = equipment.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Inicio",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = serviceRequest.startDate ?: "Sin fecha",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Fin",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = serviceRequest.endDate ?: "Sin fecha",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Precio
            Text(
                text = "S/ ${String.format("%.2f", serviceRequest.totalPrice)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun getRequestTypeLabel(type: String): String {
    return when (type) {
        "rental" -> "Alquiler"
        "maintenance" -> "Mantenimiento"
        "installation" -> "Instalación"
        else -> type
    }
}

@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor, label) = when (status) {
        "pending" -> Triple(Color(0xFFFFF3CD), Color(0xFF856404), "Pendiente")
        "in_progress" -> Triple(Color(0xFFCCE5FF), Color(0xFF004085), "En Progreso")
        "completed" -> Triple(Color(0xFFD4EDDA), Color(0xFF155724), "Completada")
        "cancelled" -> Triple(Color(0xFFF8D7DA), Color(0xFF721C24), "Cancelada")
        else -> Triple(Color.LightGray, Color.DarkGray, status)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}