package pe.edu.upc.polarnet.features.provider.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.provider.home.domain.repositories.ServiceRequestRepository
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import javax.inject.Inject

@HiltViewModel
class ProviderHomeViewModel @Inject constructor(
    private val repository: ServiceRequestRepository
) : ViewModel() {

    private val _serviceRequests = MutableStateFlow<List<ServiceRequest>>(emptyList())
    val serviceRequests: StateFlow<List<ServiceRequest>> = _serviceRequests.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Filtro por estado
    private val _selectedFilter = MutableStateFlow("all") // "all", "pending", "in_progress", "completed"
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    init {
        loadServiceRequests()
    }

    fun loadServiceRequests() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val requests = repository.getAllServiceRequests()
                _serviceRequests.value = requests
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar solicitudes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByStatus(status: String) {
        _selectedFilter.value = status

        viewModelScope.launch {
            _isLoading.value = true

            try {
                val requests = if (status == "all") {
                    repository.getAllServiceRequests()
                } else {
                    repository.getServiceRequestsByStatus(status)
                }
                _serviceRequests.value = requests
            } catch (e: Exception) {
                _errorMessage.value = "Error al filtrar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obtener solicitudes filtradas (alternativa sin llamar API nuevamente)
    fun getFilteredRequests(): List<ServiceRequest> {
        return if (_selectedFilter.value == "all") {
            _serviceRequests.value
        } else {
            _serviceRequests.value.filter { it.status == _selectedFilter.value }
        }
    }

    // Aceptar solicitud (cambiar a "completed")
    fun acceptServiceRequest(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("ProviderHomeVM", "Aceptando solicitud ID: $id")

            try {
                val result = repository.updateServiceRequestStatus(id, "completed")

                result.onSuccess {
                    android.util.Log.d("ProviderHomeVM", "Solicitud aceptada exitosamente")
                    android.util.Log.d("ProviderHomeVM", "Recargando lista de solicitudes...")
                    // Recargar lista y esperar a que termine antes de navegar
                    loadServiceRequests()
                    // Dar tiempo para que la UI se actualice
                    kotlinx.coroutines.delay(500)
                    onSuccess()
                }.onFailure { exception ->
                    android.util.Log.e("ProviderHomeVM", "Error al aceptar: ${exception.message}")
                    _errorMessage.value = exception.message
                    onError(exception.message ?: "Error desconocido")
                }
            } catch (e: Exception) {
                android.util.Log.e("ProviderHomeVM", "Excepción: ${e.message}", e)
                _errorMessage.value = e.message
                onError(e.message ?: "Error desconocido")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Rechazar solicitud (eliminar)
    fun rejectServiceRequest(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("ProviderHomeVM", "Rechazando solicitud ID: $id")

            try {
                val result = repository.deleteServiceRequest(id)

                result.onSuccess {
                    android.util.Log.d("ProviderHomeVM", "Solicitud rechazada/eliminada exitosamente")
                    android.util.Log.d("ProviderHomeVM", "Recargando lista de solicitudes...")
                    // Recargar lista y esperar a que termine antes de navegar
                    loadServiceRequests()
                    // Dar tiempo para que la UI se actualice
                    kotlinx.coroutines.delay(500)
                    onSuccess()
                }.onFailure { exception ->
                    android.util.Log.e("ProviderHomeVM", "Error al rechazar: ${exception.message}")
                    _errorMessage.value = exception.message
                    onError(exception.message ?: "Error desconocido")
                }
            } catch (e: Exception) {
                android.util.Log.e("ProviderHomeVM", "Excepción: ${e.message}", e)
                _errorMessage.value = e.message
                onError(e.message ?: "Error desconocido")
            } finally {
                _isLoading.value = false
            }
        }
    }
}