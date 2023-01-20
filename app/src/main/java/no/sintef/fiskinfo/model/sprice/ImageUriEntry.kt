package no.sintef.fiskinfo.model.sprice

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.File

@Entity
data class ImageUriEntry(
    @ColumnInfo(name = "fileUriPath") val file: File,
    @ColumnInfo(name = "webKitFormBoundaryId") val webKitFormBoundaryId: String
)