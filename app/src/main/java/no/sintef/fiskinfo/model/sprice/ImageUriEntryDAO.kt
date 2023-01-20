package no.sintef.fiskinfo.model.sprice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageUriEntryDAO {
    @Query("SELECT * FROM ImageUriEntry")
    fun getAll(): List<ImageUriEntry>

    @Query("SELECT * FROM ImageUriEntry WHERE webKitFormBoundaryId LIKE :first")
    fun getAllWithId(id: String): List<ImageUriEntry>

    @Insert
    fun insertAll(vararg ImageUriEntry: ImageUriEntry)

    @Delete
    fun delete(imageUriEntry: ImageUriEntry)
}