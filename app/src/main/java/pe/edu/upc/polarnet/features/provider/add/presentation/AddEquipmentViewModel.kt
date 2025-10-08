package pe.edu.upc.polarnet.features.provider.addequipment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.provider.add.domain.repositories.AddEquipmentRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import javax.inject.Inject

@HiltViewModel
class AddEquipmentViewModel @Inject constructor(
    private val repository: AddEquipmentRepository
) : ViewModel() {

    // Estados del formulario
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _brand = MutableStateFlow("")
    val brand: StateFlow<String> = _brand.asStateFlow()

    private val _model = MutableStateFlow("")
    val model: StateFlow<String> = _model.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _thumbnail = MutableStateFlow("")
    val thumbnail: StateFlow<String> = _thumbnail.asStateFlow()

    private val _pricePerMonth = MutableStateFlow("")
    val pricePerMonth: StateFlow<String> = _pricePerMonth.asStateFlow()

    private val _purchasePrice = MutableStateFlow("")
    val purchasePrice: StateFlow<String> = _purchasePrice.asStateFlow()

    private val _location = MutableStateFlow("Lima, Perú")
    val location: StateFlow<String> = _location.asStateFlow()

    private val _available = MutableStateFlow(true)
    val available: StateFlow<Boolean> = _available.asStateFlow()

    // Estados de UI
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Categorías predefinidas
    val categories = listOf(
        "Cámaras Frigoríficas",
        "Congeladores",
        "Refrigeradores",
        "Vitrinas",
        "Otros"
    )

    // Actualizar campos
    fun updateName(value: String) { _name.value = value }
    fun updateBrand(value: String) { _brand.value = value }
    fun updateModel(value: String) { _model.value = value }
    fun updateCategory(value: String) { _category.value = value }
    fun updateDescription(value: String) { _description.value = value }
    fun updateThumbnail(value: String) { _thumbnail.value = value }
    fun updatePricePerMonth(value: String) { _pricePerMonth.value = value }
    fun updatePurchasePrice(value: String) { _purchasePrice.value = value }
    fun updateLocation(value: String) { _location.value = value }
    fun updateAvailable(value: Boolean) { _available.value = value }

    // Validación
    private fun validateForm(): String? {
        if (_name.value.isBlank()) return "El nombre es obligatorio"
        if (_category.value.isBlank()) return "La categoría es obligatoria"
        if (_pricePerMonth.value.isBlank()) return "El precio por mes es obligatorio"
        if (_purchasePrice.value.isBlank()) return "El precio de compra es obligatorio"

        try {
            _pricePerMonth.value.toDouble()
        } catch (e: Exception) {
            return "El precio por mes debe ser un número válido"
        }

        try {
            _purchasePrice.value.toDouble()
        } catch (e: Exception) {
            return "El precio de compra debe ser un número válido"
        }

        return null
    }

    // Guardar equipo
    fun addEquipment(providerId: Long, onSuccess: () -> Unit) {
        val validationError = validateForm()
        if (validationError != null) {
            _errorMessage.value = validationError
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val equipment = Equipment(
                    id = 0, // Se generará en el backend
                    providerId = providerId,
                    name = _name.value,
                    brand = _brand.value.ifBlank { null },
                    model = _model.value.ifBlank { null },
                    category = _category.value,
                    description = _description.value.ifBlank { null },
                    thumbnail = _thumbnail.value.ifBlank { null },
                    specifications = null,
                    available = _available.value,
                    location = _location.value,
                    pricePerMonth = _pricePerMonth.value.toDouble(),
                    purchasePrice = _purchasePrice.value.toDouble(),
                    createdAt = null,
                    updatedAt = null
                )

                repository.addEquipment(equipment)
                _successMessage.value = "Equipo agregado exitosamente"
                clearForm()
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al agregar equipo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Limpiar formulario
    private fun clearForm() {
        _name.value = ""
        _brand.value = ""
        _model.value = ""
        _category.value = ""
        _description.value = ""
        _thumbnail.value = ""
        _pricePerMonth.value = ""
        _purchasePrice.value = ""
        _location.value = "Lima, Perú"
        _available.value = true
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}