package pe.edu.upc.polarnet.features.home.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import pe.edu.upc.polarnet.features.home.data.local.dao.EquipmentDao
import pe.edu.upc.polarnet.features.home.data.local.database.AppDatabase

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
    fun provideEquipmentDao(db: AppDatabase): EquipmentDao {
        return db.equipmentDao()
    }
}
