package pe.edu.upc.polarnet.core.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import pe.edu.upc.polarnet.core.database.AppDatabase
import pe.edu.upc.polarnet.features.client.equipments.data.local.dao.ClientEquipmentDao
import pe.edu.upc.polarnet.features.client.home.data.local.dao.EquipmentDao


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "polarnet_db"
        ).build()
    }

    @Provides
    fun provideEquipmentDao(db: AppDatabase): EquipmentDao = db.equipmentDao()

    @Provides
    fun provideClientEquipmentDao(db: AppDatabase): ClientEquipmentDao = db.clientEquipmentDao()
}
