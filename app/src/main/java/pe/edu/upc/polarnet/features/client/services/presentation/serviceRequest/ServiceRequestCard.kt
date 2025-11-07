package pe.edu.upc.polarnet.features.client.services.presentation.serviceRequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.polarnet.shared.models.ServiceRequest

@Composable
fun ServiceRequestCard(request: ServiceRequest) {
    val statusColor = when (request.status.lowercase()) {
        "completed" -> MaterialTheme.colorScheme.primaryContainer
        "in_progress" -> MaterialTheme.colorScheme.tertiaryContainer
        "pending" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(statusColor.copy(alpha = 0.3f))
            .padding(16.dp)
    ) {
        Text(
            text = request.requestType.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Estado: ${request.status}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "Equipo ID: ${request.equipmentId}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "Precio: S/. ${request.totalPrice}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        request.startDate?.let {
            Text(
                text = "Inicio: $it",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        request.endDate?.let {
            Text(
                text = "Fin: $it",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (!request.notes.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Notas: ${request.notes}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
