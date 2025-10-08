package pe.edu.upc.polarnet.features.provider.add.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.provider.add.data.remote.services.AddEquipmentService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddEquipmentModule {

    @Provides
    @Singleton
    fun provideAddEquipmentService(retrofit: Retrofit): AddEquipmentService {
        return retrofit.create(AddEquipmentService::class.java)
    }
}