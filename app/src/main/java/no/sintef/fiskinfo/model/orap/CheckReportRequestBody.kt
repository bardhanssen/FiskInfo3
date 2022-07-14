package no.sintef.fiskinfo.model.orap

import no.sintef.fiskinfo.util.OrapUtils
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.ZoneId

class CheckReportRequestBody(
    val WebKitFormBoundaryId: String,
    val Username: String = "",
    val Password: String = "",
    val Meldingstype: String = "",
    val ReportingTime: String = "",
    val ObservationDate: String = "",
    val ObservationTime: String = "",
    val VesselCallSign: String = "",
    val CardinalDirectionLatitude: String = "",
    val Latitude: String = "",
    val CardinalDirectionLongitude: String = "",
    val Longitude: String = "",
    val SignAirTemperature: String = "",
    val AirTemperature: String = "",
    val SignSeaTemperature: String = "",
    val SeaTemperature: String = "",
    val MaxMiddleWindTime: MaxMiddleWindTimeEnum = MaxMiddleWindTimeEnum.DURING_OBSERVATION,
    val MaxMiddelWindInKnots: String = "",
    val StrongestWindGustInKnots: String = "",
    val HeightOfWindWavesInMeters: String = "",
    val PeriodForWindWavesInSeconds: String = "",
    val HeightForFirstSwellSystemInMeters: String = "",
    val PeriodForFirstSwellSystemInSeconds: String = "",
    val DirectionForFirstSwellSystemInDegrees: String = "",
    val ConcentrationOfSeaIce: String = "",
    val SeaIceStageOfDevelopment: String = "",
    val OceanIce: String = "",
    val DirectionToNearestIceEdge: String = "",
    val SeaIceConditionsAndDevelopmentTheLastThreeHours: String = "",
    val ReasonForIcing: String = "",
    val IceThicknessInCm: String = "",
    val ChangeInIce: String = "",
    val Action: String = OrapConstants.FormValues.ACTION_CHECK_MESSAGE
) {
    private constructor(builder: Builder) : this(
        builder.WebKitFormBoundaryId,
        builder.Username,
        builder.Password,
        builder.Meldingstype,
        builder.ReportingTime,
        builder.ObservationDate,
        builder.ObservationTime,
        builder.VesselCallSign,
        builder.CardinalDirectionLatitude,
        builder.Latitude,
        builder.CardinalDirectionLongitude,
        builder.Longitude,
        builder.SignAirTemperature,
        builder.AirTemperature,
        builder.SignSeaTemperature,
        builder.SeaTemperature,
        builder.MaxMiddleWindTime,
        builder.MaxMiddelWindInKnots,
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
        builder.ChangeInIce,
        builder.Action,
    )

    data class Builder(
        var WebKitFormBoundaryId: String = OrapUtils.getBoundaryIdString(),
        var Username: String = "",
        var Password: String = "",
        var Meldingstype: String = "",
        var ReportingTime: String = "",
        var ObservationDate: String = "",
        var ObservationTime: String = "",
        var VesselCallSign: String = "",
        var CardinalDirectionLatitude: String = "",
        var Latitude: String = "",
        var CardinalDirectionLongitude: String = "",
        var Longitude: String = "",
        var SignAirTemperature: String = "",
        var AirTemperature: String = "",
        var SignSeaTemperature: String = "",
        var SeaTemperature: String = "",
        var MaxMiddleWindTime: MaxMiddleWindTimeEnum = MaxMiddleWindTimeEnum.NOT_SELECTED,
        var MaxMiddelWindInKnots: String = "",
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
        var IceThicknessInCm: String = "",
        var ChangeInIce: String = "",
        var Action: String = OrapConstants.FormValues.ACTION_CHECK_MESSAGE

    ) {
        fun WebKitFormBoundaryId(webKitFormBoundaryId: String) = apply { this.WebKitFormBoundaryId = webKitFormBoundaryId }
        fun Username(username: String) = apply { this.Username = username }
        fun Password(password: String) = apply { this.Password = password }
        fun Meldingstype(meldingstype: String) = apply { this.Meldingstype = meldingstype }
        fun ReportingTime(reportingTime: String) = apply { this.ReportingTime = reportingTime }
        fun ObservationDate(observationDate: String) = apply { this.ObservationDate = observationDate }
        fun ObservationTime(observationTime: String) = apply { this.ObservationTime = observationTime }
        fun VesselCallSign(vesselCallSign: String) = apply { this.VesselCallSign = vesselCallSign }
        fun CardinalDirectionLatitude(cardinalDirectionLatitude: String) = apply { this.CardinalDirectionLatitude = cardinalDirectionLatitude }
        fun Latitude(latitude: String) = apply { this.Latitude = latitude }
        fun CardinalDirectionLongitude(cardinalDirectionLongitude: String) = apply { this.CardinalDirectionLongitude = cardinalDirectionLongitude }
        fun Longitude(longitude: String) = apply { this.Longitude = longitude }
        fun SignAirTemperature(signAirTemperature: String) = apply { this.SignAirTemperature = signAirTemperature }
        fun AirTemperature(airTemperature: String) = apply { this.AirTemperature = airTemperature }
        fun SignSeaTemperature(signSeaTemperature: String) = apply { this.SignSeaTemperature = signSeaTemperature }
        fun SeaTemperature(seaTemperature: String) = apply { this.SeaTemperature = seaTemperature }
        fun MaxMiddleWindTime(maxMiddleWindTime: MaxMiddleWindTimeEnum) = apply { this.MaxMiddleWindTime = maxMiddleWindTime }
        fun MaxMiddelWindInKnots(maxMiddelWindInKnots: String) = apply { this.MaxMiddelWindInKnots = maxMiddelWindInKnots }
        fun StrongestWindGustInKnots(strongestWindGustInKnots: String) = apply { this.StrongestWindGustInKnots = strongestWindGustInKnots }
        fun HeightOfWindWavesInMeters(heightOfWindWavesInMeters: String) = apply { this.HeightOfWindWavesInMeters = heightOfWindWavesInMeters }
        fun PeriodForWindWavesInSeconds(periodForWindWavesInSeconds: String) = apply { this.PeriodForWindWavesInSeconds = periodForWindWavesInSeconds }
        fun HeightForFirstSwellSystemInMeters(heightForFirstSwellSystemInMeters: String) = apply { this.HeightForFirstSwellSystemInMeters = heightForFirstSwellSystemInMeters }
        fun PeriodForFirstSwellSystemInSeconds(periodForFirstSwellSystemInSeconds: String) = apply { this.PeriodForFirstSwellSystemInSeconds = periodForFirstSwellSystemInSeconds }
        fun DirectionForFirstSwellSystemInDegrees(directionForFirstSwellSystemInDegrees: String) = apply { this.DirectionForFirstSwellSystemInDegrees = directionForFirstSwellSystemInDegrees }
        fun ConcentrationOfSeaIce(concentrationOfSeaIce: String) = apply { this.ConcentrationOfSeaIce = concentrationOfSeaIce }
        fun SeaIceStageOfDevelopment(seaIceStageOfDevelopment: String) = apply { this.SeaIceStageOfDevelopment = seaIceStageOfDevelopment }
        fun OceanIce(oceanIce: String) = apply { this.OceanIce = oceanIce }
        fun DirectionToNearestIceEdge(directionToNearestIceEdge: String) = apply { this.DirectionToNearestIceEdge = directionToNearestIceEdge }
        fun SeaIceConditionsAndDevelopmentTheLastThreeHours(seaIceConditionsAndDevelopmentTheLastThreeHours: String) = apply { this.SeaIceConditionsAndDevelopmentTheLastThreeHours = seaIceConditionsAndDevelopmentTheLastThreeHours }
        fun ReasonForIcing(reasonForIcing: String) = apply { this.ReasonForIcing = reasonForIcing }
        fun IceThicknessInCm(iceThicknessInCm: String) = apply { this.IceThicknessInCm = iceThicknessInCm }
        fun ChangeInIce(changeInIce: String) = apply { this.ChangeInIce = changeInIce }
        fun Action(action: String) = apply { this.Action = action }

        fun Build(): CheckReportRequestBody {
            return CheckReportRequestBody(this)
        }
    }

    fun GetAsRequestBodyString(): String {
        val messageReceivedTime = LocalDateTime.now() // TODO: Get as GMT+0
        val ObservationTimeStamp = LocalDateTime.now() // TODO: Get as GMT+0
        val zoneId = ZoneId.systemDefault()
        val reportingTimeEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond()
        val observationEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond() // TODO: Get from user

        val messageTag =
            OrapUtils.getOrapMessageTag(messageReceivedTime, Username)
        val synopTimeStamp = OrapUtils.getFormattedTimeStamp(ObservationTimeStamp, OrapConstants.SYNOP_DAY1_TIMESTAMP)

        val stringBuilder = StringBuilder()

        stringBuilder.append(
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.USER, Username),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.PASSWORD, Password),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.MESSAGE_TYPE, OrapConstants.FormValues.REPORT_MESSAGE_TYPE),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.OBS_STATIC, observationEpoch),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SYNOP_DAY1, synopTimeStamp),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SYNOP1, ObservationTimeStamp.hour),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.CAL1, VesselCallSign),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_LALA1, CardinalDirectionLatitude),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.LALA1, Latitude),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_LOLO1, CardinalDirectionLongitude),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.LOLO1, Longitude),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_TT, SignAirTemperature),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.TT1, AirTemperature),
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.SIGN_TW, SignSeaTemperature),
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
            OrapUtils.getValueAsWebKitForm(WebKitFormBoundaryId, OrapConstants.FormDataNames.RS1, Action),
            OrapUtils.getWebFormKitEndTag(WebKitFormBoundaryId)
        )

        return stringBuilder.toString();
    }
}