package pe.edu.upc.polarnet.features.client.home.presentation.home

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
class HomeViewModel @Inject constructor(
    private val repository: EquipmentRepository
) : ViewModel() {

    private val _equipments = MutableStateFlow<List<Equipment>>(emptyList())
    val equipments: StateFlow<List<Equipment>> = _equipments

    fun getAllEquipments() {
        viewModelScope.launch {
            _equipments.value = repository.getAllEquipments()
        }
    }

    init {
        getAllEquipments()
    }
}
