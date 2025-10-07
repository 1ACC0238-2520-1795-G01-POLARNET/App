package pe.edu.upc.polarnet.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import io.github.jan.supabase.SupabaseClient
import pe.edu.upc.polarnet.core.networking.SupabaseClient as SupabaseCoreClient

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient = SupabaseCoreClient.client
}
