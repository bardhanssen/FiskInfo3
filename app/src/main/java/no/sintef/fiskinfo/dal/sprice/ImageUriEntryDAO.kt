package no.sintef.fiskinfo.dal.sprice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageUriEntryDAO {
    @Query("SELECT * FROM imageUris")
    suspend fun getAll(): List<ImageUriEntry>

    @Query("SELECT * FROM imageUris WHERE webKitFormBoundaryId LIKE :id")
    suspend fun getAllWithId(id: String): List<ImageUriEntry>

    @Insert
    suspend fun insertAll(imageUriEntry: List<ImageUriEntry>)

    @Delete
    suspend fun delete(imageUriEntry: ImageUriEntry)
}