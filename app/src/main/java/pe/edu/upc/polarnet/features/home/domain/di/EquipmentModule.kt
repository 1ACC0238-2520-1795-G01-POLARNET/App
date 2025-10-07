package pe.edu.upc.polarnet.features.home.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.polarnet.features.home.data.remote.services.EquipmentService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EquipmentModule {

    private const val BASE_URL = "https://ivbtkzjqjjblwcokutkk.supabase.co/rest/v1/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideEquipmentService(retrofit: Retrofit): EquipmentService {
        return retrofit.create(EquipmentService::class.java)
    }
}
