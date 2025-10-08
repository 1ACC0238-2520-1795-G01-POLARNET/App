package pe.edu.upc.polarnet.features.provider.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.polarnet.features.provider.home.presentation.home.ProviderHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderHomeScreen(
    viewModel: ProviderHomeViewModel = hiltViewModel(),
    onTapServiceRequest: (Long) -> Unit
) {
    val serviceRequests = viewModel.serviceRequests.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val selectedFilter = viewModel.selectedFilter.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitudes de Servicio", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.loadServiceRequests() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filtros de estado
            StatusFilterRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { viewModel.filterByStatus(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contenido
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadServiceRequests() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                serviceRequests.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay solicitudes",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(serviceRequests) { request ->
                            ServiceRequestCard(
                                serviceRequest = request,
                                onClick = { onTapServiceRequest(request.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusFilterRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf(
        "all" to "Todas",
        "pending" to "Pendientes",
        "in_progress" to "En Progreso",
        "completed" to "Completadas"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { (key, label) ->
            FilterChip(
                selected = selectedFilter == key,
                onClick = { onFilterSelected(key) },
                label = { Text(label) }
            )
        }
    }
}