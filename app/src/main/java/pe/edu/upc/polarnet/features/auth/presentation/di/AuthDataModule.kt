package pe.edu.upc.polarnet.features.auth.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.auth.data.repositories.AuthRepositoryImpl
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthDataModule {

    @Binds
    @Singleton
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}