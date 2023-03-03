package no.sintef.fiskinfo.dal.sprice

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.sintef.fiskinfo.R

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere
    @Provides
    fun provideYourDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        SpriceDatabase::class.java,
        app.getString(R.string.sprice_database_name)
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideImageUriEntryDao(db: SpriceDatabase) = db.getImageUriEntryDAO()

    @Singleton
    @Provides
    fun provideIcingReportDao(db: SpriceDatabase) = db.getIcingReportDAO()
}