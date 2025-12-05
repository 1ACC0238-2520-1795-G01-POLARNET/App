package pe.edu.upc.polarnet.features.client.notifications.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.notifications.data.repositories.NotificationRepositoryImpl
import pe.edu.upc.polarnet.features.client.notifications.domain.repositories.NotificationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository
}
