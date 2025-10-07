package pe.edu.upc.polarnet.features.client.equipments.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.equipments.data.repositories.ClientEquipmentRepositoryImpl
import pe.edu.upc.polarnet.features.client.equipments.domain.repositories.ClientEquipmentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ClientEquipmentRepositoryModule {

    @Binds
    @Singleton
    fun bindClientEquipmentRepository(
        impl: ClientEquipmentRepositoryImpl
    ): ClientEquipmentRepository
}
