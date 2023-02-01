package no.sintef.fiskinfo.dal.sprice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload

@Dao
interface IcingReportDAO {
    @Query("SELECT * FROM IcingReports")
    suspend fun getAll(): List<ReportIcingRequestPayload>

    @Query("SELECT * FROM IcingReports WHERE webKitFormBoundaryId LIKE :id")
    suspend fun getWithId(id: String): List<ReportIcingRequestPayload>

    @Insert
    suspend fun insertAll(reportIcingRequestBody: List<ReportIcingRequestPayload>)

    @Delete()
    suspend fun delete(reportIcingRequestBody: ReportIcingRequestPayload)
}