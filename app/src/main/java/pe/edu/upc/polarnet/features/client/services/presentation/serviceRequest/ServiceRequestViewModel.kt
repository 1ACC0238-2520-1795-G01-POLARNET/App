package pe.edu.upc.polarnet.features.client.services.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.services.domain.repositories.ServiceRequestRepository
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import javax.inject.Inject

@HiltViewModel
class ServiceRequestViewModel @Inject constructor(
    private val repository: ServiceRequestRepository
) : ViewModel() {

    private val _serviceRequests = MutableStateFlow<List<ServiceRequest>>(emptyList())
    val serviceRequests: StateFlow<List<ServiceRequest>> = _serviceRequests

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getServiceRequestsByClient(clientId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = repository.getServiceRequestsByClient(clientId)
                _serviceRequests.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
