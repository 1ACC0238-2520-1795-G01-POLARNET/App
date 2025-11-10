package pe.edu.upc.polarnet.features.client.rental.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.rental.data.repositories.RentalRepositoryImpl
import pe.edu.upc.polarnet.features.client.rental.domain.repositories.RentalRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RentalRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRentalRepository(
        impl: RentalRepositoryImpl
    ): RentalRepository
}

