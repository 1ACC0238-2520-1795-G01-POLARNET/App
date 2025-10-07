package pe.edu.upc.polarnet.features.client.services.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.services.data.repositories.ServiceRequestRepositoryImpl
import pe.edu.upc.polarnet.features.client.services.domain.repositories.ServiceRequestRepository

@Module
@InstallIn(SingletonComponent::class)
interface ServiceRequestRepositoryModule {

    @Binds
    fun bindServiceRequestRepository(
        impl: ServiceRequestRepositoryImpl
    ): ServiceRequestRepository
}
