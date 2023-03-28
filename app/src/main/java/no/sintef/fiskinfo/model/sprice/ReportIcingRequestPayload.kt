/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.model.sprice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import no.sintef.fiskinfo.model.sprice.enums.ChangeInIcingOnVesselOrPlatformEnum
import no.sintef.fiskinfo.model.sprice.enums.MaxMiddleWindTimeEnum
import no.sintef.fiskinfo.model.sprice.enums.ReasonForIcingOnVesselOrPlatformEnum
import no.sintef.fiskinfo.model.sprice.enums.SeaIceConditionsAndDevelopmentEnum
import no.sintef.fiskinfo.util.SpriceUtils
import no.sintef.fiskinfo.util.SpriceUtils.Companion.getPostRequestContentTypeBoundaryValueAsString
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity(tableName = "IcingReports")
class ReportIcingRequestPayload internal constructor(
    @PrimaryKey
    @ColumnInfo(name = "WebKitFormBoundaryId") internal val WebKitFormBoundaryId: String,
    @ColumnInfo(name = "ReportingTime") internal val ReportingTime: ZonedDateTime,
    @ColumnInfo(name = "Synop") internal val Synop: ZonedDateTime,

    @ColumnInfo(name = "Username") internal val Username: String,
    @ColumnInfo(name = "Password") internal val Password: String,

    @ColumnInfo(name = "VesselCallSign") internal val VesselCallSign: String,
    @ColumnInfo(name = "Latitude") internal val Latitude: String,
    @ColumnInfo(name = "Longitude") internal val Longitude: String,

    @ColumnInfo(name = "AirTemperature") internal val AirTemperature: String,
    @ColumnInfo(name = "SeaTemperature") internal val SeaTemperature: String,

    @ColumnInfo(name = "MaxMiddleWindTime") internal val MaxMiddleWindTime: MaxMiddleWindTimeEnum,
    @ColumnInfo(name = "MaxMiddelWindInKnots") internal val MaxMiddelWindInKnots: String,
    @ColumnInfo(name = "StrongestWindGustInKnots") internal val StrongestWindGustInKnots: String,

    @ColumnInfo(name = "HeightOfWindWavesInMeters") internal val HeightOfWindWavesInMeters: String,
    @ColumnInfo(name = "PeriodForWindWavesInSeconds") internal val PeriodForWindWavesInSeconds: String,

    @ColumnInfo(name = "HeightForFirstSwellSystemInMeters") internal val HeightForFirstSwellSystemInMeters: String,
    @ColumnInfo(name = "PeriodForFirstSwellSystemInSeconds") internal val PeriodForFirstSwellSystemInSeconds: String,
    @ColumnInfo(name = "DirectionForFirstSwellSystemInDegrees") internal val DirectionForFirstSwellSystemInDegrees: String,

    @ColumnInfo(name = "ConcentrationOfSeaIce") internal val ConcentrationOfSeaIce: String,
    @ColumnInfo(name = "OceanIce") internal val OceanIce: String,
    @ColumnInfo(name = "DirectionToNearestIceEdge") internal val DirectionToNearestIceEdge: String,
    @ColumnInfo(name = "SeaIceConditionsAndDevelopmentTheLastThreeHours") internal val SeaIceConditionsAndDevelopmentTheLastThreeHours: SeaIceConditionsAndDevelopmentEnum,

    @ColumnInfo(name = "CurrentIcingDegree") internal val CurrentIcingDegree: DegreeOfIcingEnum,
    @ColumnInfo(name = "ReasonForIcing") internal val ReasonForIcing: ReasonForIcingOnVesselOrPlatformEnum,
    @ColumnInfo(name = "IceThicknessInCm") internal val IceThicknessInCm: Int,
    @ColumnInfo(name = "ChangeInIce") internal val ChangeInIce: ChangeInIcingOnVesselOrPlatformEnum,
    @ColumnInfo(name = "Reported") internal val Reported: Boolean
) {
    private constructor(builder: Builder) : this(
        builder.WebKitFormBoundaryId,
        builder.ReportingTime,
        builder.Synop,
        builder.Username,
        builder.Password,
        builder.VesselCallSign,
        builder.Latitude,
        builder.Longitude,
        builder.AirTemperature,
        builder.SeaTemperature,
        builder.MaxMiddleWindTime,
        builder.MaxMiddleWindInKnots,
        builder.StrongestWindGustInKnots,
        builder.HeightOfWindWavesInMeters,
        builder.PeriodForWindWavesInSeconds,
        builder.HeightForFirstSwellSystemInMeters,
        builder.PeriodForFirstSwellSystemInSeconds,
        builder.DirectionForFirstSwellSystemInDegrees,
        builder.ConcentrationOfSeaIce,
        builder.OceanIce,
        builder.DirectionToNearestIceEdge,
        builder.SeaIceConditionsAndDevelopmentTheLastThreeHours,
        builder.CurrentIcingDegree,
        builder.ReasonForIcing,
        builder.IceThicknessInCm,
        builder.ChangeInIce,
        builder.Reported
    )

    data class Builder(
        var WebKitFormBoundaryId: String = SpriceUtils.getBoundaryIdString(),
        var ReportingTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        var Synop: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
        var Username: String = "",
        var Password: String = "",
        var VesselCallSign: String = "",
        var Latitude: String = "",
        var Longitude: String = "",
        var AirTemperature: String = "",
        var SeaTemperature: String = "",
        var MaxMiddleWindTime: MaxMiddleWindTimeEnum = MaxMiddleWindTimeEnum.DURING_OBSERVATION,
        var MaxMiddleWindInKnots: String = "",
        var StrongestWindGustInKnots: String = "",
        var HeightOfWindWavesInMeters: String = "",
        var PeriodForWindWavesInSeconds: String = "",
        var HeightForFirstSwellSystemInMeters: String = "",
        var PeriodForFirstSwellSystemInSeconds: String = "",
        var DirectionForFirstSwellSystemInDegrees: String = "",
        var ConcentrationOfSeaIce: String = "",
        var OceanIce: String = "",
        var DirectionToNearestIceEdge: String = "",
        var SeaIceConditionsAndDevelopmentTheLastThreeHours: SeaIceConditionsAndDevelopmentEnum = SeaIceConditionsAndDevelopmentEnum.NOT_SELECTED,
        var ReasonForIcing: ReasonForIcingOnVesselOrPlatformEnum = ReasonForIcingOnVesselOrPlatformEnum.NOT_SELECTED,
        var IceThicknessInCm: Int = 0,
        var CurrentIcingDegree: DegreeOfIcingEnum = DegreeOfIcingEnum.NOT_SELECTED,
        var ChangeInIce: ChangeInIcingOnVesselOrPlatformEnum = ChangeInIcingOnVesselOrPlatformEnum.NOT_SELECTED,
        var Reported: Boolean = false
    ) {
        fun webKitFormBoundaryId(webKitFormBoundaryId: String) = apply { this.WebKitFormBoundaryId = webKitFormBoundaryId }
        fun reportingTime(reportingTime: ZonedDateTime) = apply { this.ReportingTime = reportingTime }
        fun synop(synop: ZonedDateTime) = apply { this.Synop = synop }
        fun username(username: String) = apply { this.Username = username }
        fun password(password: String) = apply { this.Password = password }
        fun callSign(callSign: String) = apply { this.VesselCallSign = callSign }
        fun latitude(latitude: String) = apply { this.Latitude = latitude }
        fun longitude(longitude: String) = apply { this.Longitude = longitude }
        fun airTemperature(airTemperature: String) = apply { this.AirTemperature = airTemperature }
        fun seaTemperature(seaTemperature: String) = apply { this.SeaTemperature = seaTemperature }
        fun maxMiddleWindTime(maxMiddleWindTime: MaxMiddleWindTimeEnum) = apply { this.MaxMiddleWindTime = maxMiddleWindTime }

        fun maxMiddleWindInKnots(maxMiddleWindInKnots: String) = apply { this.MaxMiddleWindInKnots = maxMiddleWindInKnots }
        fun strongestWindGustInKnots(strongestWindGustInKnots: String) = apply { this.StrongestWindGustInKnots = strongestWindGustInKnots }
        fun heightOfWindWavesInMeters(heightOfWindWavesInMeters: String) =
            apply { this.HeightOfWindWavesInMeters = heightOfWindWavesInMeters }

        fun periodForWindWavesInSeconds(periodForWindWavesInSeconds: String) =
            apply { this.PeriodForWindWavesInSeconds = periodForWindWavesInSeconds }

        fun heightForFirstSwellSystemInMeters(heightForFirstSwellSystemInMeters: String) =
            apply { this.HeightForFirstSwellSystemInMeters = heightForFirstSwellSystemInMeters }

        fun periodForFirstSwellSystemInSeconds(periodForFirstSwellSystemInSeconds: String) =
            apply { this.PeriodForFirstSwellSystemInSeconds = periodForFirstSwellSystemInSeconds }

        fun directionForFirstSwellSystemInDegrees(directionForFirstSwellSystemInDegrees: String) =
            apply { this.DirectionForFirstSwellSystemInDegrees = directionForFirstSwellSystemInDegrees }

        fun concentrationOfSeaIce(concentrationOfSeaIce: String) =
            apply { this.ConcentrationOfSeaIce = concentrationOfSeaIce }

        fun oceanIce(oceanIce: String) = apply { this.OceanIce = oceanIce }
        fun directionToNearestIceEdge(directionToNearestIceEdge: String) =
            apply { this.DirectionToNearestIceEdge = directionToNearestIceEdge }

        fun seaIceConditionsAndDevelopmentTheLastThreeHours(
            seaIceConditionsAndDevelopmentTheLastThreeHours: SeaIceConditionsAndDevelopmentEnum
        ) = apply {
            this.SeaIceConditionsAndDevelopmentTheLastThreeHours =
                seaIceConditionsAndDevelopmentTheLastThreeHours
        }

        fun reasonForIcing(reasonForIcing: ReasonForIcingOnVesselOrPlatformEnum) = apply { this.ReasonForIcing = reasonForIcing }
        fun currentIcingDegree(currentIcingDegree: DegreeOfIcingEnum) = apply { this.CurrentIcingDegree = currentIcingDegree}
        fun iceThicknessInCm(iceThicknessInCm: String) =
            apply {
                try {
                    this.IceThicknessInCm = iceThicknessInCm.toInt()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }

        fun changeInIce(changeInIce: ChangeInIcingOnVesselOrPlatformEnum) = apply { this.ChangeInIce = changeInIce }
        fun reported(reported: Boolean) = apply { this.Reported = reported }
        fun build(): ReportIcingRequestPayload {
            return ReportIcingRequestPayload(this)
        }
    }

    fun getRequestPayloadForSpriceEndpointReportSubmissionAsString(): String {
        val reportingZonedTime = ReportingTime
        val reportingTimeEpoch = reportingZonedTime.toEpochSecond()
        val observationEpoch = Synop.toEpochSecond()
        val stringBuilder = StringBuilder()

        val messageTag =
            SpriceUtils.getOrapMessageTag(reportingZonedTime, Username)

        val hiddenMessage = SpriceUtils.getFormattedHiddenMessageForSpriceEndpoint(Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex(), CurrentIcingDegree.getFormIndex())
        val hiddenKlMessage = SpriceUtils.getFormattedHiddenKlMessageForSpriceEndpoint(Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), CurrentIcingDegree.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex(), reportingZonedTime, Synop, reportingTimeEpoch)
        val hiddenKlStatus = SpriceUtils.getFormattedHiddenKlStatusForSpriceEndpoint(observationEpoch, Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), CurrentIcingDegree.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex())

        stringBuilder.append(
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.ACTION, SpriceConstants.FormValues.ACTION_SEND_REPORT),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.TAG, messageTag),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.REG_EPOC, reportingTimeEpoch),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.USER, Username),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.PASSWORD, Password),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.MESSAGE_TYPE, SpriceConstants.FormValues.REPORT_MESSAGE_TYPE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.LAT, ""),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.LON, ""),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.CAL, ""),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_TERMIN, observationEpoch),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_MESS, hiddenMessage),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_KL_MESS, hiddenKlMessage),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_KL_STATUS, hiddenKlStatus),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_FILE, SpriceConstants.FormValues.HIDDEN_FILE_VALUE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_TYPE, SpriceConstants.FormValues.HIDDEN_TYPE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.LSTEP, SpriceConstants.FormValues.LSTEP),
            SpriceUtils.getWebFormKitEndTag(WebKitFormBoundaryId)
        )

        return stringBuilder.toString()
    }

    fun getRequestPayloadForSpriceEndpointReportSubmissionAsRequestPayload(boundary: String): MultipartBody {
        val reportingZonedTime = ReportingTime
        val reportingTimeEpoch = reportingZonedTime.toEpochSecond()
        val observationEpoch = Synop.toEpochSecond()

        val messageTag =
            SpriceUtils.getOrapMessageTag(reportingZonedTime, Username)

        val hiddenMessage = SpriceUtils.getFormattedHiddenMessageForSpriceEndpoint(Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex(), CurrentIcingDegree.getFormIndex())
        val hiddenKlMessage = SpriceUtils.getFormattedHiddenKlMessageForSpriceEndpoint(Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), CurrentIcingDegree.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex(), reportingZonedTime, Synop, reportingTimeEpoch)
        val hiddenKlStatus = SpriceUtils.getFormattedHiddenKlStatusForSpriceEndpoint(observationEpoch, Username, VesselCallSign, Latitude, Longitude, ReasonForIcing.getFormIndex(), CurrentIcingDegree.getFormIndex(), SeaIceConditionsAndDevelopmentTheLastThreeHours.getFormIndex(), IceThicknessInCm, ChangeInIce.getFormIndex())

        val requestBody =  MultipartBody.Builder(getPostRequestContentTypeBoundaryValueAsString(boundary))
            .setType(MediaType.parse("multipart/form-data")!!)
            .addFormDataPart(SpriceConstants.FormDataNames.ACTION, SpriceConstants.FormValues.ACTION_SEND_REPORT)
            .addFormDataPart(SpriceConstants.FormDataNames.TAG, messageTag)
            .addFormDataPart(SpriceConstants.FormDataNames.REG_EPOC, reportingTimeEpoch.toString())
            .addFormDataPart(SpriceConstants.FormDataNames.USER, Username)
            .addFormDataPart(SpriceConstants.FormDataNames.PASSWORD, Password)
            .addFormDataPart(SpriceConstants.FormDataNames.MESSAGE_TYPE, SpriceConstants.FormValues.REPORT_MESSAGE_TYPE)
            .addFormDataPart(SpriceConstants.FormDataNames.LAT, "")
            .addFormDataPart(SpriceConstants.FormDataNames.LON, "")
            .addFormDataPart(SpriceConstants.FormDataNames.CAL, "")
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_TERMIN, observationEpoch.toString())
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_MESS, hiddenMessage)
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_KL_MESS, hiddenKlMessage)
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_KL_STATUS, hiddenKlStatus)
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_FILE, SpriceConstants.FormValues.HIDDEN_FILE_VALUE)
            .addFormDataPart(SpriceConstants.FormDataNames.HIDDEN_TYPE, SpriceConstants.FormValues.HIDDEN_TYPE)
            .addFormDataPart(SpriceConstants.FormDataNames.LSTEP, SpriceConstants.FormValues.LSTEP.toString())

        return requestBody.build()
    }

    fun getRequestPayloadForReportSubmissionAsString(): String {
        val reportingZonedTime = ReportingTime
        val reportingTimeEpoch = reportingZonedTime.toEpochSecond()
        val observationEpoch = Synop.toEpochSecond()
        val stringBuilder = StringBuilder()

        val messageTag =
            SpriceUtils.getOrapMessageTag(reportingZonedTime, Username)

        val hiddenMessage = SpriceUtils.getFormattedHiddenMessage(Username, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature)
        val hiddenKlMessage = SpriceUtils.getFormattedHiddenKlMessage(Username, reportingZonedTime, Synop, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature, reportingTimeEpoch)
        val hiddenKlStatus = SpriceUtils.getFormattedHiddenKlStatus(observationEpoch, Username, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature)

        stringBuilder.append(
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.ACTION, SpriceConstants.FormValues.ACTION_SEND_REPORT),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.TAG, messageTag),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.REG_EPOC, reportingTimeEpoch),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.USER, Username),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.PASSWORD, Password),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.MESSAGE_TYPE, SpriceConstants.FormValues.REPORT_MESSAGE_TYPE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_TERMIN, observationEpoch),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_MESS, hiddenMessage),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_KL_MESS, hiddenKlMessage),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_KL_STATUS, hiddenKlStatus),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_FILE, SpriceConstants.FormValues.HIDDEN_FILE_VALUE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.HIDDEN_TYPE, SpriceConstants.FormValues.HIDDEN_TYPE),
            SpriceUtils.getValueAsWebKitForm(WebKitFormBoundaryId, SpriceConstants.FormDataNames.LSTEP, SpriceConstants.FormValues.LSTEP),
            SpriceUtils.getWebFormKitEndTag(WebKitFormBoundaryId)
        )

        return stringBuilder.toString()
    }
}