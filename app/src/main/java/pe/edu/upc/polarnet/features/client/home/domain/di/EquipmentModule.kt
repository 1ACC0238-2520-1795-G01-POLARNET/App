package pe.edu.upc.polarnet.features.client.home.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.home.data.remote.services.EquipmentService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EquipmentModule {

    @Provides
    @Singleton
    fun provideEquipmentService(retrofit: Retrofit): EquipmentService {
        return retrofit.create(EquipmentService::class.java)
    }
}
