package pe.edu.upc.polarnet.features.provider.inventory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

@HiltViewModel
class ProviderInventoryViewModel @Inject constructor(
    private val repository: EquipmentRepository
) : ViewModel() {

    private val _equipments = MutableStateFlow<List<Equipment>>(emptyList())
    val equipments: StateFlow<List<Equipment>> = _equipments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Filtro por categoría
    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Filtro por disponibilidad
    private val _showOnlyAvailable = MutableStateFlow(false)
    val showOnlyAvailable: StateFlow<Boolean> = _showOnlyAvailable.asStateFlow()

    fun loadProviderEquipments(providerId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val allEquipments = repository.getAllEquipments()
                // Filtrar solo los equipos del provider actual
                _equipments.value = allEquipments.filter { it.providerId == providerId }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar equipos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleAvailabilityFilter() {
        _showOnlyAvailable.value = !_showOnlyAvailable.value
    }

    // Obtener equipos filtrados
    fun getFilteredEquipments(): List<Equipment> {
        var filtered = _equipments.value

        // Filtrar por categoría
        if (_selectedCategory.value != "all") {
            filtered = filtered.filter { it.category == _selectedCategory.value }
        }

        // Filtrar por disponibilidad
        if (_showOnlyAvailable.value) {
            filtered = filtered.filter { it.available }
        }

        return filtered
    }

    // Obtener categorías únicas
    fun getCategories(): List<String> {
        return _equipments.value.map { it.category }.distinct().sorted()
    }

    fun refreshEquipments(providerId: Long) {
        loadProviderEquipments(providerId)
    }
}