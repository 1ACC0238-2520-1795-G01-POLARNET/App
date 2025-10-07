package pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

@HiltViewModel
class EquipmentDetailViewModel @Inject constructor(
    private val repository: EquipmentRepository
) : ViewModel() {

    private val _equipment = MutableStateFlow<Equipment?>(null)
    val equipment: StateFlow<Equipment?> = _equipment

    fun getEquipmentById(id: Long) {
        viewModelScope.launch {
            _equipment.value = repository.getEquipmentById(id)
        }
    }
}
