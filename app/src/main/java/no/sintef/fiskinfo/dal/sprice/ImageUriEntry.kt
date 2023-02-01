package no.sintef.fiskinfo.dal.sprice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageUris")
data class ImageUriEntry(
    @ColumnInfo(name = "fileUriPath") val file: String,
    @ColumnInfo(name = "webKitFormBoundaryId") val webKitFormBoundaryId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}