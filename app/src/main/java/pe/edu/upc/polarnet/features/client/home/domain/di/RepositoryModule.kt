package pe.edu.upc.polarnet.features.client.home.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.home.data.repositories.EquipmentRepositoryImpl
import pe.edu.upc.polarnet.features.client.home.domain.repositories.EquipmentRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindEquipmentRepository(
        impl: EquipmentRepositoryImpl
    ): EquipmentRepository
}
