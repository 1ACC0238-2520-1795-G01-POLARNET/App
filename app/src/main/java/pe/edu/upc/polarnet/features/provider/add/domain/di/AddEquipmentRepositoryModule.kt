package pe.edu.upc.polarnet.features.provider.add.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.provider.add.data.repositories.AddEquipmentRepositoryImpl
import pe.edu.upc.polarnet.features.provider.add.domain.repositories.AddEquipmentRepository

@Module
@InstallIn(SingletonComponent::class)
interface AddEquipmentRepositoryModule {

    @Binds
    fun bindAddEquipmentRepository(
        impl: AddEquipmentRepositoryImpl
    ): AddEquipmentRepository
}