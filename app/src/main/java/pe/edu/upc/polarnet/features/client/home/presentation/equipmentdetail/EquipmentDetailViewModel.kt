package pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository
import pe.edu.upc.polarnet.features.client.rental.domain.repositories.RentalRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import javax.inject.Inject

@HiltViewModel
class EquipmentDetailViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val rentalRepository: RentalRepository
) : ViewModel() {

    private val _equipment = MutableStateFlow<Equipment?>(null)
    val equipment: StateFlow<Equipment?> = _equipment

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _rentalSuccess = MutableStateFlow(false)
    val rentalSuccess: StateFlow<Boolean> = _rentalSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getEquipmentById(id: Long) {
        viewModelScope.launch {
            _equipment.value = equipmentRepository.getEquipmentById(id)
        }
    }

    fun createRentalRequest(
        clientId: Long,
        equipmentId: Long,
        rentalMonths: Int,
        pricePerMonth: Double
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                _rentalSuccess.value = false

                Log.d("EquipmentDetailVM", "====================================")
                Log.d("EquipmentDetailVM", "🔥 CREANDO SOLICITUD DE RENTA")
                Log.d("EquipmentDetailVM", "====================================")
                Log.d("EquipmentDetailVM", "📋 Cliente ID: $clientId")
                Log.d("EquipmentDetailVM", "📦 Equipo ID: $equipmentId")
                Log.d("EquipmentDetailVM", "📅 Meses: $rentalMonths")
                Log.d("EquipmentDetailVM", "💰 Precio/mes: $pricePerMonth")

                // Usar SimpleDateFormat compatible con API 24
                val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                val calendar = java.util.Calendar.getInstance()
                val startDate = formatter.format(calendar.time)

                calendar.add(java.util.Calendar.MONTH, rentalMonths)
                val endDate = formatter.format(calendar.time)

                val totalPrice = pricePerMonth * rentalMonths

                val serviceRequest = ServiceRequest(
                    id = 0,
                    clientId = clientId,
                    equipmentId = equipmentId,
                    requestType = "rental",
                    description = "Solicitud de renta por $rentalMonths mes(es)",
                    startDate = startDate,
                    endDate = endDate,
                    status = "pending",
                    totalPrice = totalPrice,
                    notes = "Solicitud creada desde la aplicación móvil",
                    createdAt = null,
                    equipment = null,
                    client = null
                )

                val result = rentalRepository.createRentalRequest(serviceRequest)

                result.onSuccess { createdRequest ->
                    Log.d("EquipmentDetailVM", "✅ Solicitud creada exitosamente: ID ${createdRequest.id}")
                    _rentalSuccess.value = true
                }.onFailure { exception ->
                    Log.e("EquipmentDetailVM", "❌ Error al crear solicitud: ${exception.message}")
                    _errorMessage.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                Log.e("EquipmentDetailVM", "❌ Excepción: ${e.message}", e)
                _errorMessage.value = e.message ?: "Error al crear solicitud"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetRentalState() {
        _rentalSuccess.value = false
        _errorMessage.value = null
    }
}
