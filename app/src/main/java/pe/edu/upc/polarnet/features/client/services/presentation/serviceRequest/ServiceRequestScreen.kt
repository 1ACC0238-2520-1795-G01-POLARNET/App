package pe.edu.upc.polarnet.features.client.services.presentation.serviceRequest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import pe.edu.upc.polarnet.features.client.services.presentation.ServiceRequestViewModel

@Composable
fun ServiceRequestScreen(
    clientId: Long,
    viewModel: ServiceRequestViewModel = hiltViewModel()
) {
    val serviceRequests = viewModel.serviceRequests.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getServiceRequestsByClient(clientId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error.value != null -> {
                Text(
                    text = "Error: ${error.value}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            serviceRequests.value.isEmpty() -> {
                Text(
                    text = "No hay servicios registrados",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(serviceRequests.value) { request ->
                        ServiceRequestCard(request)
                    }
                }
            }
        }
    }
}

