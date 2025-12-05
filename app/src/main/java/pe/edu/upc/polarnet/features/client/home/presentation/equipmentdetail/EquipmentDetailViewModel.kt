package pe.edu.upc.polarnet.features.client.home.presentation.equipmentdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.equipments.domain.repositories.ClientEquipmentRepository
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository
import pe.edu.upc.polarnet.features.client.notifications.domain.repositories.NotificationRepository
import pe.edu.upc.polarnet.features.client.rental.domain.repositories.RentalRepository
import pe.edu.upc.polarnet.shared.models.Equipment
import pe.edu.upc.polarnet.shared.models.Notification
import pe.edu.upc.polarnet.shared.models.ServiceRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EquipmentDetailViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository,
    private val rentalRepository: RentalRepository,
    private val notificationRepository: NotificationRepository,
    private val clientEquipmentRepository: ClientEquipmentRepository
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
                Log.d("EquipmentDetailVM", "CREANDO SOLICITUD DE RENTA")
                Log.d("EquipmentDetailVM", "====================================")
                Log.d("EquipmentDetailVM", "Cliente ID: $clientId")
                Log.d("EquipmentDetailVM", "Equipo ID: $equipmentId")
                Log.d("EquipmentDetailVM", "Meses: $rentalMonths")
                Log.d("EquipmentDetailVM", "Precio/mes: $pricePerMonth")

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
                    Log.d("EquipmentDetailVM", "Solicitud creada exitosamente: ID ${createdRequest.id}")

                    // Crear el registro en client_equipment
                    createClientEquipmentRecord(
                        clientId = clientId,
                        equipmentId = equipmentId,
                        startDate = startDate,
                        endDate = endDate
                    )

                    // Crear notificaciones para el cliente y el proveedor
                    val providerId = _equipment.value?.providerId ?: 0L
                    createRentalNotifications(
                        clientId = clientId,
                        providerId = providerId,
                        equipmentName = _equipment.value?.name ?: "Equipo",
                        totalPrice = totalPrice,
                        rentalMonths = rentalMonths
                    )

                    _rentalSuccess.value = true
                }.onFailure { exception ->
                    Log.e("EquipmentDetailVM", "Error al crear solicitud: ${exception.message}")
                    _errorMessage.value = exception.message
                }
            } catch (e: Exception) {
                Log.e("EquipmentDetailVM", "Excepción: ${e.message}", e)
                _errorMessage.value = e.message ?: "Error al crear solicitud"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun createRentalNotifications(
        clientId: Long,
        providerId: Long,
        equipmentName: String,
        totalPrice: Double,
        rentalMonths: Int
    ) {
        viewModelScope.launch {
            try {
                val timestampFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val timestamp = timestampFormatter.format(Date())

                // Notificación para el CLIENTE
                val clientNotification = Notification(
                    id = UUID.randomUUID().toString(),
                    userId = clientId.toString(),
                    role = "client",
                    type = "rental_created",
                    title = "¡Solicitud enviada!",
                    message = "Has solicitado alquilar \"$equipmentName\" por $rentalMonths mes(es). Total: S/ ${String.format(Locale.getDefault(), "%.2f", totalPrice)}. Tu solicitud está pendiente de aprobación.",
                    timestamp = timestamp,
                    isRead = false
                )

                notificationRepository.createNotification(clientNotification).onSuccess {
                    Log.d("EquipmentDetailVM", "Notificación para cliente creada exitosamente")
                }.onFailure { e ->
                    Log.e("EquipmentDetailVM", "Error al crear notificación del cliente: ${e.message}")
                }

                // Notificación para el PROVEEDOR
                val providerNotification = Notification(
                    id = UUID.randomUUID().toString(),
                    userId = providerId.toString(),
                    role = "provider",
                    type = "rental_request",
                    title = "¡Nueva solicitud de alquiler!",
                    message = "Un cliente ha solicitado alquilar \"$equipmentName\" por $rentalMonths mes(es). Total: S/ ${String.format(Locale.getDefault(), "%.2f", totalPrice)}",
                    timestamp = timestamp,
                    isRead = false
                )

                notificationRepository.createNotification(providerNotification).onSuccess {
                    Log.d("EquipmentDetailVM", "Notificación para proveedor creada exitosamente")
                }.onFailure { e ->
                    Log.e("EquipmentDetailVM", "Error al crear notificación del proveedor: ${e.message}")
                }

            } catch (e: Exception) {
                Log.e("EquipmentDetailVM", "Excepción al crear notificaciones: ${e.message}", e)
            }
        }
    }

    private fun createClientEquipmentRecord(
        clientId: Long,
        equipmentId: Long,
        startDate: String,
        endDate: String?
    ) {
        viewModelScope.launch {
            try {
                Log.d("EquipmentDetailVM", "Creando registro en client_equipment...")

                clientEquipmentRepository.createClientEquipment(
                    clientId = clientId,
                    equipmentId = equipmentId,
                    ownershipType = "rented",
                    startDate = startDate,
                    endDate = endDate,
                    status = "active",
                    notes = "Equipo alquilado desde la aplicación móvil"
                ).onSuccess {
                    Log.d("EquipmentDetailVM", "ClientEquipment creado exitosamente: ID ${it.id}")
                }.onFailure { e ->
                    Log.e("EquipmentDetailVM", "Error al crear ClientEquipment: ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("EquipmentDetailVM", "Excepción al crear ClientEquipment: ${e.message}", e)
            }
        }
    }

    fun resetRentalState() {
        _rentalSuccess.value = false
        _errorMessage.value = null
    }
}
