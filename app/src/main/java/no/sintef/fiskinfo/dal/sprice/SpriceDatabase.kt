package no.sintef.fiskinfo.dal.sprice

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.sintef.fiskinfo.BuildConfig
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload
import java.io.File
import javax.inject.Singleton

@Database(
    entities = [ImageUriEntry::class, ReportIcingRequestPayload::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(ZonedDateTimeConverter::class)
@Singleton
abstract class SpriceDatabase: RoomDatabase() {
    abstract fun getImageUriEntryDAO(): ImageUriEntryDAO
    abstract fun getIcingReportDAO(): IcingReportDAO

    companion object {

        private const val TAG = "AppDatabase"

        const val VERSION = 1
        private const val DATABASE_NAME = "sprice_database.db"

        @Volatile
        private var instance: SpriceDatabase? = null

        /**
         * Gets and returns the database instance if exists; otherwise, builds a new database.
         * @param context The context to access the application context.
         * @return The database instance.
         */
        fun getInstance(context: Context): SpriceDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        /**
         * Creates and returns the callback object to execute when the database is first created.
         * @return The callback object to execute when the database is first created.
         */
        private fun appDatabaseCallback(): Callback = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "Database has been created.")

                // Throws exception
                CoroutineScope(Dispatchers.IO).launch {
                    instance?.getImageUriEntryDAO()?.let { populateDbAsync(it) }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "Database has been opened.")
            }
        }

        /**
         * Builds and returns the database.
         * @param appContext The application context to reference.
         * @return The built database.
         */
        private fun buildDatabase(appContext: Context): SpriceDatabase {
            val filesDir = appContext.getExternalFilesDir(null)
            val dataDir = File(filesDir, "data")
            if (!dataDir.exists())
                dataDir.mkdir()

            val builder =
                Room.databaseBuilder(
                    appContext,
                    SpriceDatabase::class.java,
                    File(dataDir, DATABASE_NAME).toString()
                ).fallbackToDestructiveMigration()

            // Execute the callback only in DEBUG mode.
            if (BuildConfig.DEBUG) {
                builder.addCallback(appDatabaseCallback())
            }
            return builder.build()
        }

        /**
         * Populates the database when it is first created, as a suspended operation.
         * @param appDao The application DAO to execute queries.
         */
        private suspend fun populateDbAsync(appDao: ImageUriEntryDAO) {

            withContext(Dispatchers.IO) {
                // Populate your database here...
            }

        }
    }
}