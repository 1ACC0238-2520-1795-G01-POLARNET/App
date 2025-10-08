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
}