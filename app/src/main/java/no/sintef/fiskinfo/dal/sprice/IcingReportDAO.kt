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

    @Delete()
    suspend fun delete(reportIcingRequestBody: ReportIcingRequestPayload)

    @Query("SELECT * FROM IcingReports WHERE Reported = 0")
    suspend fun getUnreportedReports(): List<ReportIcingRequestPayload>
}

//The query returns some columns
//[
//WebKitFormBoundaryId, ReportingTime, Synop, Username,
//Password, VesselCallSign, Latitude, Longitude, AirTemperature, SeaTemperature,
//MaxMiddleWindTime, MaxMiddelWindInKnots, StrongestWindGustInKnots, HeightOfWindWavesInMeters,
//PeriodForWindWavesInSeconds, HeightForFirstSwellSystemInMeters, PeriodForFirstSwellSystemInSeconds,
//DirectionForFirstSwellSystemInDegrees, ConcentrationOfSeaIce, OceanIce, DirectionToNearestIceEdge,
//SeaIceConditionsAndDevelopmentTheLastThreeHours, CurrentIcingDegree, ReasonForIcing, IceThicknessInCm,
//ChangeInIce, Reported
//]
//
//which are not used by kotlin.Unit. You can use @ColumnInfo annotation on the fields to specify the mapping. You can annotate the method with @RewriteQueriesToDropUnusedColumns to direct Room to rewrite your query to avoid fetching unused columns.
//You can suppress this warning by annotating the method with @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH).
//Columns returned by the query:
//
//WebKitFormBoundaryId, ReportingTime, Synop, Username,
//Password, VesselCallSign, Latitude, Longitude, AirTemperature, SeaTemperature,
//MaxMiddleWindTime, MaxMiddelWindInKnots, StrongestWindGustInKnots, HeightOfWindWavesInMeters,
//PeriodForWindWavesInSeconds, HeightForFirstSwellSystemInMeters, PeriodForFirstSwellSystemInSeconds,
//DirectionForFirstSwellSystemInDegrees, ConcentrationOfSeaIce, OceanIce, DirectionToNearestIceEdge,
//SeaIceConditionsAndDevelopmentTheLastThreeHours, CurrentIcingDegree, ReasonForIcing, IceThicknessInCm,
//ChangeInIce, Reported.