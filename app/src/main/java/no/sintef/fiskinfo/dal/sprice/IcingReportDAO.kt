package no.sintef.fiskinfo.dal.sprice

import androidx.room.*
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
interface IcingReportDAO {
    @Query("SELECT * FROM IcingReports")
    suspend fun getAll(): List<ReportIcingRequestPayload>

    @Query("SELECT * FROM IcingReports WHERE webKitFormBoundaryId LIKE :id")
    suspend fun getWithId(id: String): List<ReportIcingRequestPayload>

    @Insert
    suspend fun insert(reportIcingRequestBody: ReportIcingRequestPayload)

    @Insert
    suspend fun insertMultiple(reportIcingRequestBodyList: List<ReportIcingRequestPayload>)

    @Delete
    suspend fun delete(reportIcingRequestBody: ReportIcingRequestPayload)

    @Query("SELECT * FROM IcingReports WHERE Reported = 0")
    suspend fun getUnreportedReports(): List<ReportIcingRequestPayload>
}