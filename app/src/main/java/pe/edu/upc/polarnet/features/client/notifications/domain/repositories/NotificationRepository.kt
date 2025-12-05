package pe.edu.upc.polarnet.features.client.notifications.domain.repositories

import pe.edu.upc.polarnet.shared.models.Notification

interface NotificationRepository {
    suspend fun getNotificationsByUserId(userId: Long): List<Notification>
    suspend fun getUnreadNotifications(userId: Long): List<Notification>
    suspend fun createNotification(notification: Notification): Result<Notification>
    suspend fun markAsRead(notificationId: String): Result<Boolean>
    suspend fun markAllAsRead(userId: Long): Result<Boolean>
    suspend fun getUnreadCount(userId: Long): Int
}

