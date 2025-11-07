package pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import pe.edu.upc.polarnet.core.ui.components.RoundedIcon

@Composable
fun EquipmentDetail(viewModel: EquipmentDetailViewModel = hiltViewModel()) {
    val equipment = viewModel.equipment.collectAsState()
    equipment.value?.let { equipment ->
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Acción de agregar al carrito o reservar
                    }
                ) {
                    Text("Add to cart")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                Box {
                    AsyncImage(
                        model = equipment.thumbnail,
                        contentDescription = equipment.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp),
                        contentScale = ContentScale.FillHeight
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        equipment.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "%.2f".format(equipment.purchasePrice),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RoundedIcon(Icons.Default.Remove)
                    Text("1", modifier = Modifier.padding(8.dp))
                    RoundedIcon(Icons.Default.Add)
                }
            }
        }
    }
}
