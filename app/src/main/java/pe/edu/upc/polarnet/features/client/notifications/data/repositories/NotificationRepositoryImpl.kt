package pe.edu.upc.polarnet.features.client.notifications.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.polarnet.features.client.notifications.data.remote.models.CreateNotificationDto
import pe.edu.upc.polarnet.features.client.notifications.data.remote.services.NotificationService
import pe.edu.upc.polarnet.features.client.notifications.domain.repositories.NotificationRepository
import pe.edu.upc.polarnet.shared.models.Notification
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val service: NotificationService
) : NotificationRepository {

    override suspend fun getNotificationsByUserId(userId: Long): List<Notification> = withContext(Dispatchers.IO) {
        try {
            Log.d("NotificationRepo", "Obteniendo notificaciones del usuario: $userId")
            val response = service.getNotificationsByUserId("eq.$userId")

            if (response.isSuccessful) {
                val notifications = response.body()?.map { dto ->
                    Notification(
                        id = dto.id,
                        userId = dto.userId,
                        role = dto.role,
                        type = dto.type,
                        title = dto.title,
                        message = dto.message,
                        timestamp = dto.timestamp,
                        isRead = dto.isRead,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt
                    )
                } ?: emptyList()

                Log.d("NotificationRepo", "Notificaciones obtenidas: ${notifications.size}")
                return@withContext notifications
            } else {
                Log.e("NotificationRepo", "Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun getUnreadNotifications(userId: Long): List<Notification> = withContext(Dispatchers.IO) {
        try {
            val response = service.getUnreadNotifications("eq.$userId")

            if (response.isSuccessful) {
                return@withContext response.body()?.map { dto ->
                    Notification(
                        id = dto.id,
                        userId = dto.userId,
                        role = dto.role,
                        type = dto.type,
                        title = dto.title,
                        message = dto.message,
                        timestamp = dto.timestamp,
                        isRead = dto.isRead,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt
                    )
                } ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception getting unread: ${e.message}", e)
        }

        return@withContext emptyList()
    }

    override suspend fun createNotification(notification: Notification): Result<Notification> = withContext(Dispatchers.IO) {
        try {
            Log.d("NotificationRepo", "Creando notificación para usuario: ${notification.userId}")

            val createDto = CreateNotificationDto(
                id = notification.id,
                userId = notification.userId,
                role = notification.role,
                type = notification.type,
                title = notification.title,
                message = notification.message,
                timestamp = notification.timestamp,
                isRead = notification.isRead
            )

            val response = service.createNotification(createDto)

            if (response.isSuccessful) {
                val dto = response.body()?.firstOrNull()
                if (dto != null) {
                    Log.d("NotificationRepo", "Notificación creada: ${dto.id}")
                    val createdNotification = Notification(
                        id = dto.id,
                        userId = dto.userId,
                        role = dto.role,
                        type = dto.type,
                        title = dto.title,
                        message = dto.message,
                        timestamp = dto.timestamp,
                        isRead = dto.isRead,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt
                    )
                    return@withContext Result.success(createdNotification)
                }
            } else {
                Log.e("NotificationRepo", "Error: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception creating notification: ${e.message}", e)
            return@withContext Result.failure(e)
        }

        return@withContext Result.failure(Exception("Error al crear notificación"))
    }

    override suspend fun markAsRead(notificationId: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = service.markAsRead(
                id = "eq.$notificationId",
                body = mapOf("is_read" to true)
            )

            if (response.isSuccessful) {
                Log.d("NotificationRepo", "Notificación marcada como leída: $notificationId")
                return@withContext Result.success(true)
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception: ${e.message}", e)
            return@withContext Result.failure(e)
        }

        return@withContext Result.failure(Exception("Error al marcar como leída"))
    }

    override suspend fun markAllAsRead(userId: Long): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = service.markAllAsRead(
                userId = "eq.$userId",
                body = mapOf("is_read" to true)
            )

            if (response.isSuccessful) {
                Log.d("NotificationRepo", "Todas las notificaciones marcadas como leídas")
                return@withContext Result.success(true)
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception: ${e.message}", e)
            return@withContext Result.failure(e)
        }

        return@withContext Result.failure(Exception("Error al marcar todas como leídas"))
    }

    override suspend fun getUnreadCount(userId: Long): Int = withContext(Dispatchers.IO) {
        try {
            val response = service.getUnreadNotifications("eq.$userId")
            if (response.isSuccessful) {
                return@withContext response.body()?.size ?: 0
            }
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Exception getting count: ${e.message}", e)
        }
        return@withContext 0
    }
}

