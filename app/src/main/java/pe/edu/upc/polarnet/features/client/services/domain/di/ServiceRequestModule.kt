package pe.edu.upc.polarnet.features.client.services.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.services.data.remote.services.ServiceRequestService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceRequestModule {

    @Provides
    @Singleton
    fun provideServiceRequestService(retrofit: Retrofit): ServiceRequestService {
        return retrofit.create(ServiceRequestService::class.java)
    }
}
