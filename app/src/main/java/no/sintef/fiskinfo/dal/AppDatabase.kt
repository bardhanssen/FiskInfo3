package no.sintef.fiskinfo.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import no.sintef.fiskinfo.model.sprice.ImageUriEntry
import no.sintef.fiskinfo.model.sprice.ImageUriEntryDAO

@Database(entities = [ImageUriEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageUriEntryDAO(): ImageUriEntryDAO
}