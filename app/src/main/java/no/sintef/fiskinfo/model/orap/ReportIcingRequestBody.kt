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
package no.sintef.fiskinfo.model.orap

import no.sintef.fiskinfo.util.OrapUtils
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.ZoneId

class ReportIcingRequestBody internal constructor(
    internal val WebKitFormBoundaryId: String,
    internal val ObservationTime: LocalDateTime,
    internal val Synop: LocalDateTime,

    internal val Username: String,
    internal val Password: String,

    internal val VesselCallSign: String,
    internal val Latitude: String,
    internal val Longitude: String,

    internal val AirTemperature: String,
    internal val SeaTemperature: String,

    internal val MaxMiddleWindTime: MaxMiddleWindTimeEnum,
    internal val MaxMiddelWindInKnots: String,
    internal val StrongestWindGustInKnots: String,

    internal val HeightOfWindWavesInMeters: String,
    internal val PeriodForWindWavesInSeconds: String,

    internal val HeightForFirstSwellSystemInMeters: String,
    internal val PeriodForFirstSwellSystemInSeconds: String,
    internal val DirectionForFirstSwellSystemInDegrees: String,

    internal val ConcentrationOfSeaIce: String,
    internal val SeaIceStageOfDevelopment: String,
    internal val OceanIce: String,
    internal val DirectionToNearestIceEdge: String,
    internal val SeaIceConditionsAndDevelopmentTheLastThreeHours: String,

    internal val ReasonForIcing: String,
    internal val IceThicknessInCm: Int,
    internal val ChangeInIce: String
) {
    private constructor(builder: Builder) : this(
        builder.WebKitFormBoundaryId,
        builder.ObservationTime,
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
        builder.SeaIceStageOfDevelopment,
        builder.OceanIce,
        builder.DirectionToNearestIceEdge,
        builder.SeaIceConditionsAndDevelopmentTheLastThreeHours,
        builder.ReasonForIcing,
        builder.IceThicknessInCm,
        builder.ChangeInIce
    )

    data class Builder(
        var WebKitFormBoundaryId: String = OrapUtils.getBoundaryIdString(),
        var ObservationTime: LocalDateTime = LocalDateTime.now(),
        var Synop: LocalDateTime = LocalDateTime.now(),
        var Username: String = "",
        var Password: String = "",
        var VesselCallSign: String = "",
        var Latitude: String = "",
        var Longitude: String = "",
        var AirTemperature: String = "",
        var SeaTemperature: String = "",
        var MaxMiddleWindTime: MaxMiddleWindTimeEnum = MaxMiddleWindTimeEnum.NOT_SELECTED,
        var MaxMiddleWindInKnots: String = "",
        var StrongestWindGustInKnots: String = "",
        var HeightOfWindWavesInMeters: String = "",
        var PeriodForWindWavesInSeconds: String = "",
        var HeightForFirstSwellSystemInMeters: String = "",
        var PeriodForFirstSwellSystemInSeconds: String = "",
        var DirectionForFirstSwellSystemInDegrees: String = "",
        var ConcentrationOfSeaIce: String = "",
        var SeaIceStageOfDevelopment: String = "",
        var OceanIce: String = "",
        var DirectionToNearestIceEdge: String = "",
        var SeaIceConditionsAndDevelopmentTheLastThreeHours: String = "",
        var ReasonForIcing: String = "",
        var IceThicknessInCm: Int = -1,
        var ChangeInIce: String = ""
    ) {
        fun webKitFormBoundaryId(webKitFormBoundaryId: String) = apply { this.WebKitFormBoundaryId = webKitFormBoundaryId }
        fun observationTime(observationTime: LocalDateTime) =
            apply { this.ObservationTime = observationTime }

        fun synop(synop: LocalDateTime) = apply { this.Synop = synop }
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

        fun seaIceStageOfDevelopment(seaIceStageOfDevelopment: String) =
            apply { this.SeaIceStageOfDevelopment = seaIceStageOfDevelopment }

        fun oceanIce(oceanIce: String) = apply { this.OceanIce = oceanIce }
        fun DirectionToNearestIceEdge(directionToNearestIceEdge: String) =
            apply { this.DirectionToNearestIceEdge = directionToNearestIceEdge }

        fun seaIceConditionsAndDevelopmentTheLastThreeHours(
            seaIceConditionsAndDevelopmentTheLastThreeHours: String
        ) = apply {
            this.SeaIceConditionsAndDevelopmentTheLastThreeHours =
                seaIceConditionsAndDevelopmentTheLastThreeHours
        }

        fun reasonForIcing(reasonForIcing: String) = apply { this.ReasonForIcing = reasonForIcing }
        fun iceThicknessInCm(iceThicknessInCm: Int) =
            apply { this.IceThicknessInCm = iceThicknessInCm }

        fun changeInIce(changeInIce: String) = apply { this.ChangeInIce = changeInIce }

        fun build(): ReportIcingRequestBody {
            return ReportIcingRequestBody(this)
        }
    }

    fun getRequestBodyForReportSubmissionAsString(): String {
        val stringBuilder = StringBuilder()

        val messageReceivedTime = LocalDateTime.now() // TODO: Get as GMT+0
        val zoneId = ZoneId.systemDefault()
        val reportingTimeEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond() // TODO: Get from user
        val observationEpoch = Synop.atZone(zoneId).toEpochSecond() // TODO: Get from user

        val messageTag =
            OrapUtils.getOrapMessageTag(messageReceivedTime, Username)

        val hiddenMessage = OrapUtils.getFormattedHiddenMessage(Username, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature)
        val hiddenKlMessage = OrapUtils.getFormattedHiddenKlMessage(Username, messageReceivedTime, Synop, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature, reportingTimeEpoch)
        val hiddenKlStatus = OrapUtils.getFormattedHiddenKlStatus(observationEpoch, Username, HeightOfWindWavesInMeters, PeriodForWindWavesInSeconds, IceThicknessInCm, Latitude, Longitude, AirTemperature)

        stringBuilder.append(
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.ACTION, OrapConstants.FormValues.ACTION_SEND_REPORT),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.TAG, messageTag),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.REG_EPOC, reportingTimeEpoch),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.USER, Username),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.PASSWORD, Password),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.MESSAGE_TYPE, OrapConstants.FormValues.REPORT_MESSAGE_TYPE),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_TERMIN, observationEpoch),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_MESS, hiddenMessage),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_KL_MESS, hiddenKlMessage),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_KL_STATUS, hiddenKlStatus),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_FILE, OrapConstants.FormValues.HIDDEN_FILE_VALUE),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HIDDEN_TYPE, OrapConstants.FormValues.HIDDEN_TYPE),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.LSTEP, OrapConstants.FormValues.LSTEP),
            OrapUtils.getWebFormKitEndTag(WebKitFormBoundaryId)
        )

        return stringBuilder.toString();
    }

    fun getRequestBodyForReportCheckAsString(): String {
        val messageReceivedTime = LocalDateTime.now() // TODO: Get as GMT+0
        val ObservationTimeStamp = LocalDateTime.now() // TODO: Get as GMT+0
        val zoneId = ZoneId.systemDefault()
        val reportingTimeEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond() // TODO: Get from user
        val observationEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond() // TODO: Get from user

        val messageTag =
            OrapUtils.getOrapMessageTag(messageReceivedTime, Username)
        val synopTimeStamp = OrapUtils.getFormattedTimeStamp(ObservationTimeStamp, OrapConstants.SYNOP_DAY1_TIMESTAMP)

        val stringBuilder = StringBuilder()

        stringBuilder.append(
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.USER, Username),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.PASSWORD, Password),
            OrapUtils.getValueAsWebKitForm(
                WebKitFormBoundaryId,
                OrapConstants.FormDataNames.MESSAGE_TYPE,
                OrapConstants.FormValues.REPORT_MESSAGE_TYPE
            ),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.OBS_STATIC, observationEpoch),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SYNOP_DAY1, synopTimeStamp),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SYNOP1, ObservationTimeStamp.hour),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.CAL1, VesselCallSign),
            OrapUtils.getValueAsWebKitForm(
                WebKitFormBoundaryId,
                OrapConstants.FormDataNames.SIGN_LALA1,
                if ('-' == Latitude.first()) CardinalDirections.S.toString() else CardinalDirections.N.toString()
            ),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.LALA1, Latitude),
            OrapUtils.getValueAsWebKitForm(
                WebKitFormBoundaryId,
                OrapConstants.FormDataNames.SIGN_LOLO1,
                if ('-' == Longitude.first()) CardinalDirections.W.toString() else CardinalDirections.E.toString()
            ),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.LOLO1, Longitude),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_TT, if ('-' == AirTemperature.first()) "-" else "+"),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.TT1, AirTemperature),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_TW, if ('-' == SeaTemperature.first()) "-" else "+"),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.TW1, SeaTemperature),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.TZ1, MaxMiddleWindTime.getFormValue()),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.FX1, MaxMiddelWindInKnots),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.FG1, StrongestWindGustInKnots),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HW1, HeightOfWindWavesInMeters),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.PW1, PeriodForWindWavesInSeconds),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.HW11, HeightForFirstSwellSystemInMeters),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.PW11, PeriodForFirstSwellSystemInSeconds),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.DW11, DirectionForFirstSwellSystemInDegrees),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.CI1, ConcentrationOfSeaIce),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SI1, SeaIceStageOfDevelopment),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.BI1, OceanIce),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.DI1, DirectionToNearestIceEdge),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.ZI1, SeaIceConditionsAndDevelopmentTheLastThreeHours),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.IS1, ReasonForIcing),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.ESES1, IceThicknessInCm),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.RS1, ChangeInIce),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.RS1, OrapConstants.FormValues.ACTION_CHECK_MESSAGE),
            OrapUtils.getWebFormKitEndTag(WebKitFormBoundaryId)
        )

        return stringBuilder.toString();
    }
}