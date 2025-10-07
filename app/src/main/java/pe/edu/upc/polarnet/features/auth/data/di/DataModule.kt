package pe.edu.upc.polarnet.features.auth.data.di

import pe.edu.upc.polarnet.features.auth.data.repositories.AuthRepositoryImpl
import pe.edu.upc.polarnet.features.auth.domain.repositories.AuthRepository

object DataModule {

    fun getAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }
}