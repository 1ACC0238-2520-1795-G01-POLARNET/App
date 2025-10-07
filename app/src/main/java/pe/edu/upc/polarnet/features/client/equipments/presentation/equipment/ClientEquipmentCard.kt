package pe.edu.upc.polarnet.features.client.equipments.presentation.equipment

import pe.edu.upc.polarnet.shared.models.ClientEquipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ClientEquipmentCard(
    clientEquipment: ClientEquipment,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = clientEquipment.equipment?.name ?: "Equipo sin nombre",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Estado: ${clientEquipment.status}",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tipo: ${clientEquipment.ownershipType}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Inicio: ${clientEquipment.startDate}",
                style = MaterialTheme.typography.bodySmall
            )
            clientEquipment.endDate?.let {
                Text(text = "Fin: $it", style = MaterialTheme.typography.bodySmall)
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(onClick = onClick) {
                    Icon(Icons.Default.Build, contentDescription = null)
                    Text("Detalles")
                }
            }
        }
    }
}