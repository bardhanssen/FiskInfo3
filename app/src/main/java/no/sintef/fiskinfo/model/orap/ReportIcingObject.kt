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

class ReportIcingObject internal constructor(
    val webKitFormBoundaryId: String = OrapUtils.GetBoundaryIdString(OrapConstants.BOUNDARY_ID_LENGTH),
    val Id: Int,
    val ObservationTime: LocalDateTime,
    val Synop: LocalDateTime,

    val Username: String,
    val Password: String,

    val CallSign: String,
    val Latitude: Float?,
    val Longitude: Float?,

    val AirTemperature: Float?,
    val SeaTemperature: Float?,

    val MaxMiddleWindTime: String,
    val MaxMiddelWindInKnots: Float?,
    val StrongestWindGustInKnots: Float?,

    val HeightOfWindWavesInMeters: Float?,
    val PeriodForWindWavesInSeconds: Int?,

    val HeightForFirstSwellSystemInMeters: Float?,
    val PeriodForFirstSwellSystemInSeconds: Float?,
    val DirectionForFirstSwellSystemInDegrees: Float?,

    val ConcentrationOfSeaIce: String,
    val SeaIceStageOfDevelopment: String,
    val OceanIce: String,
    val DirectionToNearestIceEdge: String,
    val SeaIceConditionsAndDevelopmentTheLastThreeHours: String,

    val ReasonForIcing: String,
    val IceThicknessInCm: Int?,
    val ChangeInIce: String
) {
    data class Builder(
        var Id: Int = 0,
        var ObservationTime: LocalDateTime? = null,
        var Synop: String = "",
        var Username: String = "",
        var Password: String = "",
        var CallSign: String = "",
        var Latitude: Float? = 0.0F,
        var Longitude: Float? = 0.0F,
        var AirTemperature: Float? = 0.0F,
        var SeaTemperature: Float? = 0.0F,
        var MaxMiddleWindTime: String = "",
        var MaxMiddleWindInKnots: Float? = null,
        var StrongestWindGustInKnots: Float? = null,
        var HeightOfWindWavesInMeters: Float? = 0.0F,
        var PeriodForWindWavesInSeconds: Float? = 0.0F,
        var HeightForFirstSwellSystemInMeters: Float? = 0.0F,
        var PeriodForFirstSwellSystemInSeconds: Float? = 0.0F,
        var DirectionForFirstSwellSystemInDegrees: Float? = 0.0F,
        var ConcentrationOfSeaIce: String = "",
        var SeaIceStageOfDevelopment: String = "",
        var OceanIce: String = "",
        var DirectionToNearestIceEdge: Float? = null,
        var SeaIceConditionsAndDevelopmentTheLastThreeHours: String = "",
        var ReasonForIcing: String = "",
        var IceThicknessInCm: Float? = 0.0F,
        var ChangeInIce: String = ""
    ) {
        fun Id(id: Int) = apply { this.Id = id }
        fun ObservationTime(observationTime: LocalDateTime) =
            apply { this.ObservationTime = observationTime }

        fun Synop(synop: String) = apply { this.Synop = synop }
        fun Username(username: String) = apply { this.Username = username }
        fun Password(password: String) = apply { this.Password = password }
        fun CallSign(callSign: String) = apply { this.CallSign = callSign }
        fun Latitude(latitude: Float) = apply { this.Latitude = latitude }
        fun Longitude(longitude: Float) = apply { this.Longitude = longitude }
        fun AirTemperature(airTemperature: Float) = apply { this.AirTemperature = airTemperature }
        fun SeaTemperature(seaTemperature: Float) = apply { this.SeaTemperature = seaTemperature }
        fun MaxMiddleWindTime(maxMiddleWindTime: String) =
            apply { this.MaxMiddleWindTime = maxMiddleWindTime }

        fun MaxMiddelWindInKnots(maxMiddelWindInKnots: Float) =
            apply { this.MaxMiddleWindInKnots = maxMiddelWindInKnots }

        fun StrongestWindGustInKnots(strongestWindGustInKnots: Float) =
            apply { this.StrongestWindGustInKnots = strongestWindGustInKnots }

        fun HeightOfWindWavesInMeters(heightOfWindWavesInMeters: Float) =
            apply { this.HeightOfWindWavesInMeters = heightOfWindWavesInMeters }

        fun PeriodForWindWavesInSeconds(periodForWindWavesInSeconds: Float) =
            apply { this.PeriodForWindWavesInSeconds = periodForWindWavesInSeconds }

        fun HeightForFirstSwellSystemInMeters(heightForFirstSwellSystemInMeters: Float) =
            apply { this.HeightForFirstSwellSystemInMeters = heightForFirstSwellSystemInMeters }

        fun PeriodForFirstSwellSystemInSeconds(periodForFirstSwellSystemInSeconds: Float) =
            apply { this.PeriodForFirstSwellSystemInSeconds = periodForFirstSwellSystemInSeconds }

        fun DirectionForFirstSwellSystemInDegrees(directionForFirstSwellSystemInDegrees: Float) =
            apply {
                this.DirectionForFirstSwellSystemInDegrees = directionForFirstSwellSystemInDegrees
            }

        fun ConcentrationOfSeaIce(concentrationOfSeaIce: String) =
            apply { this.ConcentrationOfSeaIce = concentrationOfSeaIce }

        fun SeaIceStageOfDevelopment(seaIceStageOfDevelopment: String) =
            apply { this.SeaIceStageOfDevelopment = seaIceStageOfDevelopment }

        fun OceanIce(oceanIce: String) = apply { this.OceanIce = oceanIce }
        fun DirectionToNearestIceEdge(directionToNearestIceEdge: Float) =
            apply { this.DirectionToNearestIceEdge = directionToNearestIceEdge }

        fun SeaIceConditionsAndDevelopmentTheLastThreeHours(
            seaIceConditionsAndDevelopmentTheLastThreeHours: String
        ) = apply {
            this.SeaIceConditionsAndDevelopmentTheLastThreeHours =
                seaIceConditionsAndDevelopmentTheLastThreeHours
        }

        fun ReasonForIcing(reasonForIcing: String) = apply { this.ReasonForIcing = reasonForIcing }
        fun IceThicknessInCm(iceThicknessInCm: Float) =
            apply { this.IceThicknessInCm = iceThicknessInCm }

        fun ChangeInIce(changeInIce: String) = apply { this.ChangeInIce = changeInIce }
    }

    fun GetAsRequestBody(): String {
        val messageReceivedTime = LocalDateTime.now() // TODO: Get as GMT+0
        val zoneId = ZoneId.systemDefault()
        val reportingTimeEpoch = messageReceivedTime.atZone(zoneId).toEpochSecond()
        val observationEpoch = Synop.atZone(zoneId).toEpochSecond()

        val messageTag =
            OrapUtils.GetOrapMessageTag(messageReceivedTime, Username)

        val hiddenMessage =
            "012345678      ${Username},17,,,,,,,,,,,${HeightOfWindWavesInMeters},${PeriodForWindWavesInSeconds},,,,,,,,,,,,,,,${IceThicknessInCm},,${Latitude},${Longitude},1,,,,,,\n\n"
        val hiddenKlMessage =
            "kldata/nationalnr=${Username}/type=317/test/received_time=\"${
                OrapUtils.GetFormattedTimeStamp(
                    messageReceivedTime,
                    OrapConstants.HIDDEN_KL_MESSAGE_RECEIVED_TIME_FORMAT
                )
            }\"\n" +
                    "IX,WW,VV,HL,NN,NH,CL,CM,CH,W1,W2,HW,PW,DW1,PW1,HW1,DW2,PW2,HW2,DD,FF,CI,SI,BI,DI,ZI,XIS,ES,ERS,MLAT,MLON,TA,UU,UH,PR,PO,PP,AA,MDIR,MSPEED\n" +
                    "${
                        OrapUtils.GetFormattedTimeStamp(
                            Synop,
                            OrapConstants.HIDDEN_KL_MESSAGE_OBSERVATION_TIMESTAMP
                        )
                    },3,,,,,,,,,,,${HeightOfWindWavesInMeters},${PeriodForWindWavesInSeconds},,,,,,,,,,,,,,,${IceThicknessInCm},,${Latitude},${Longitude},1,-6,,,,,,,\n" +
                    "Orap_smsformat_input ${reportingTimeEpoch} ${Username},17,,,,,,,,,,,${HeightOfWindWavesInMeters},${PeriodForWindWavesInSeconds},,,,,,,,,,,,,,,${IceThicknessInCm},,${Latitude},${Longitude},1,,,,,,\n" +
                    "Local_kvalobs_data /var/www/orap//orap_data//xenial-test//317/1/${Username}/orap_${
                        OrapUtils.GetFormattedTimeStamp(
                            messageReceivedTime,
                            OrapConstants.HIDDEN_KL_MESSAGE_RECEIVED_FILE_NAME_TIMESTAMP
                        )
                    }.txt;"
        val hiddenKlStatus =
            "${observationEpoch * 10} || 012345678      ${Username},17,,,,,,,,,,,${HeightOfWindWavesInMeters},${PeriodForWindWavesInSeconds},,,,,,,,,,,,,,,${IceThicknessInCm},,${Latitude},${Longitude},1,,,,,,\n"


        val stringBuilder = StringBuilder();

        stringBuilder.append(
            GetValueAsWebKitForm(OrapConstants.FormDataNames.ACTION, OrapConstants.FormValues.ACTION_SUBMIT_REPORT),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.TAG, messageTag),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.REG_EPOC, reportingTimeEpoch),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.USER, Username),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.PASSWORD, Password),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.MESSAGE_TYPE, OrapConstants.FormValues.REPORT_MESSAGE_TYPE),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_TERMIN, observationEpoch),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_MESS, hiddenMessage),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_KL_MESS, hiddenKlMessage),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_KL_STATUS, hiddenKlStatus),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_FILE, OrapConstants.FormValues.HIDDEN_FILE_VALUE),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_TYPE, OrapConstants.FormValues.HIDDEN_TYPE),
            GetValueAsWebKitForm(OrapConstants.FormDataNames.LSTEP, OrapConstants.FormValues.LSTEP),
            GetWebFormKitEndTag()
        )

        return stringBuilder.toString();
    }

    fun GetValueAsWebKitForm(actionName: String, value: String): String {
        return "------WebKitFormBoundary${webKitFormBoundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
    }

    fun GetValueAsWebKitForm(actionName: String, value: Long): String {
        return "------WebKitFormBoundary${webKitFormBoundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
    }

    fun GetValueAsWebKitForm(actionName: String, value: Int): String {
        return "------WebKitFormBoundary${webKitFormBoundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
    }

    fun GetWebFormKitEndTag(): String {
        return "------WebKitFormBoundary${webKitFormBoundaryId}--"
    }
}