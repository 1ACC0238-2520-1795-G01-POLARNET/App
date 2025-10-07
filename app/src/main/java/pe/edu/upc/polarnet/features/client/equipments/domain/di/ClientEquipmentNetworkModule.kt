package pe.edu.upc.polarnet.features.client.equipments.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.equipments.data.remote.services.ClientEquipmentService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientEquipmentNetworkModule {

    @Provides
    @Singleton
    fun provideClientEquipmentService(retrofit: Retrofit): ClientEquipmentService {
        return retrofit.create(ClientEquipmentService::class.java)
    }
}
