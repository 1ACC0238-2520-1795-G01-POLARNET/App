package pe.edu.upc.polarnet.features.client.notifications.data.remote.services

import pe.edu.upc.polarnet.features.client.notifications.data.remote.models.CreateNotificationDto
import pe.edu.upc.polarnet.features.client.notifications.data.remote.models.NotificationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationService {

    @GET("notifications")
    suspend fun getNotificationsByUserId(
        @Query("user_id") userId: String,
        @Query("order") order: String = "timestamp.desc",
        @Query("select") select: String = "*"
    ): Response<List<NotificationDto>>

    @GET("notifications")
    suspend fun getUnreadNotifications(
        @Query("user_id") userId: String,
        @Query("is_read") isRead: String = "eq.false",
        @Query("order") order: String = "timestamp.desc",
        @Query("select") select: String = "*"
    ): Response<List<NotificationDto>>

    @POST("notifications")
    suspend fun createNotification(
        @Body notification: CreateNotificationDto,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<NotificationDto>>

    @PATCH("notifications")
    suspend fun markAsRead(
        @Query("id") id: String,
        @Body body: Map<String, Boolean>,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<NotificationDto>>

    @PATCH("notifications")
    suspend fun markAllAsRead(
        @Query("user_id") userId: String,
        @Query("is_read") isRead: String = "eq.false",
        @Body body: Map<String, Boolean>,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<List<NotificationDto>>
}

