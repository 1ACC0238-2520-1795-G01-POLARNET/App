package pe.edu.upc.polarnet.features.client.home.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import pe.edu.upc.polarnet.core.networking.SupabaseClient as SupabaseCoreClient
import io.github.jan.supabase.SupabaseClient

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return SupabaseCoreClient.client
    }
}
