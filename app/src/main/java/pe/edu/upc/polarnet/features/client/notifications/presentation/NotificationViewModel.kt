package pe.edu.upc.polarnet.features.client.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.polarnet.features.client.notifications.domain.repositories.NotificationRepository
import pe.edu.upc.polarnet.shared.models.Notification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadNotifications(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = repository.getNotificationsByUserId(userId)
                _notifications.value = result
                _unreadCount.value = result.count { !it.isRead }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadUnreadCount(userId: Long) {
        viewModelScope.launch {
            _unreadCount.value = repository.getUnreadCount(userId)
        }
    }

    fun markAsRead(notificationId: String, userId: Long) {
        viewModelScope.launch {
            repository.markAsRead(notificationId).onSuccess {
                loadNotifications(userId)
            }
        }
    }

    fun markAllAsRead(userId: Long) {
        viewModelScope.launch {
            repository.markAllAsRead(userId).onSuccess {
                loadNotifications(userId)
            }
        }
    }

    fun createRentalNotification(
        userId: Long,
        equipmentName: String,
        totalPrice: Double,
        rentalMonths: Int
    ) {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val timestamp = formatter.format(Date())

            val notification = Notification(
                id = UUID.randomUUID().toString(),
                userId = userId.toString(),
                role = "client",
                type = "rental_created",
                title = "¡Alquiler registrado!",
                message = "Has alquilado $equipmentName por $rentalMonths mes(es). Total: S/ ${String.format("%.2f", totalPrice)}",
                timestamp = timestamp,
                isRead = false
            )

            repository.createNotification(notification).onSuccess {
                loadUnreadCount(userId)
            }
        }
    }
}

