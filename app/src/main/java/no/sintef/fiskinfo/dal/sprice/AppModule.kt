package no.sintef.fiskinfo.dal.sprice

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SpriceDatabase::class.java,
        "your_db_name"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideImageUriEntryDao(db: SpriceDatabase) = db.getImageUriEntryDAO() // The reason we can implement a Dao for the database
}