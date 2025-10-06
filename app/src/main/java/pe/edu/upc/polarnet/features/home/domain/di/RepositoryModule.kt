package pe.edu.upc.polarnet.features.home.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pe.edu.upc.polarnet.features.home.data.repositories.ProductRepositoryImpl
import pe.edu.upc.polarnet.features.home.domain.repositories.ProductRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideProductRepository(impl: ProductRepositoryImpl): ProductRepository
}