package pe.edu.upc.polarnet.features.client.rental.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.client.rental.data.remote.services.RentalService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RentalServiceModule {

    @Provides
    @Singleton
    fun provideRentalService(retrofit: Retrofit): RentalService {
        return retrofit.create(RentalService::class.java)
    }
}

