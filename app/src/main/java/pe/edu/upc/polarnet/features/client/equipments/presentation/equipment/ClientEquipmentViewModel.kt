package pe.edu.upc.polarnet.features.client.equipments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.equipments.domain.repositories.ClientEquipmentRepository
import pe.edu.upc.polarnet.shared.models.ClientEquipment
import javax.inject.Inject

@HiltViewModel
class ClientEquipmentViewModel @Inject constructor(
    private val repository: ClientEquipmentRepository
) : ViewModel() {

    private val _clientEquipments = MutableStateFlow<List<ClientEquipment>>(emptyList())
    val clientEquipments: StateFlow<List<ClientEquipment>> = _clientEquipments

    fun loadClientEquipments(clientId: Long) {
        viewModelScope.launch {
            _clientEquipments.value = repository.getClientEquipments(clientId)
        }
    }
}
