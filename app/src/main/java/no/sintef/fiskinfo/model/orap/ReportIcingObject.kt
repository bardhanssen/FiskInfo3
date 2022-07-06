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

import java.lang.StringBuilder
import java.time.LocalDateTime

class ReportIcingObject private constructor(
    val Id: Int,
    val ObservationTime: LocalDateTime?,
    val Synop: String?,

    val CallSign: String?,
    val Latitude: Float?,
    val Longitude: Float?,

    val AirTemperature: Float?,
    val SeaTemperature: Float?,

    val MaxMiddleWindTime: String?,
    val MaxMiddelWindInKnots: Float?,
    val StrongestWindGustInKnots: Float?,

    val HeightOfWindWavesInMeters: Float?,
    val PeriodForWindWavesInSeconds: Float?,

    val HeightForFirstSwellSystemInMeters: Float?,
    val PeriodForFirstSwellSystemInSeconds: Float?,
    val DirectionForFirstSwellSystemInDegrees: Float?,

    val ConcentrationOfSeaIce: String?,
    val SeaIceStageOfDevelopment: String?,
    val OceanIce: String?,
    val DirectionToNearestIceEdge: Float?,
    val SeaIceConditionsAndDevelopmentTheLastThreeHours: String?,

    val ReasonForIcing: String?,
    val IceThicknessInCm: Float?,
    val ChangeInIce: String?
) {
    data class Builder(
        var Id: Int = 0,
        var ObservationTime: LocalDateTime? = null,
        var Synop: String? = null,
        var CallSign: String? = null,
        var Latitude: Float? = 0.0F,
        var Longitude: Float? = 0.0F,
        var AirTemperature: Float? = 0.0F,
        var SeaTemperature: Float? = 0.0F,
        var MaxMiddleWindTime: String? = null,
        var MaxMiddelWindInKnots: Float? = null,
        var StrongestWindGustInKnots: Float? = null,
        var HeightOfWindWavesInMeters: Float? = 0.0F,
        var PeriodForWindWavesInSeconds: Float? = 0.0F,
        var HeightForFirstSwellSystemInMeters: Float? = 0.0F,
        var PeriodForFirstSwellSystemInSeconds: Float? = 0.0F,
        var DirectionForFirstSwellSystemInDegrees: Float? = 0.0F,
        var ConcentrationOfSeaIce: String? = null,
        var SeaIceStageOfDevelopment: String? = null,
        var OceanIce: String? = null,
        var DirectionToNearestIceEdge: Float? = null,
        var SeaIceConditionsAndDevelopmentTheLastThreeHours: String? = null,
        var ReasonForIcing: String? = null,
        var IceThicknessInCm: Float? = 0.0F,
        var ChangeInIce: String? = null
    ) {
        fun Id(id: Int) = apply {this.Id = id}
        fun ObservationTime(observationTime: LocalDateTime) = apply {this.ObservationTime = observationTime}
        fun Synop(synop: String) = apply {this.Synop = synop}
        fun CallSign(callSign: String) = apply {this.CallSign = callSign}
        fun Latitude(latitude: Float) = apply {this.Latitude = latitude}
        fun Longitude(longitude: Float) = apply {this.Longitude = longitude}
        fun AirTemperature(airTemperature: Float) = apply {this.AirTemperature = airTemperature}
        fun SeaTemperature(seaTemperature: Float) = apply {this.SeaTemperature = seaTemperature}
        fun MaxMiddleWindTime(maxMiddleWindTime: String) = apply {this.MaxMiddleWindTime = maxMiddleWindTime}
        fun MaxMiddelWindInKnots(maxMiddelWindInKnots: Float) = apply {this.MaxMiddelWindInKnots = maxMiddelWindInKnots}
        fun StrongestWindGustInKnots(strongestWindGustInKnots: Float) = apply {this.StrongestWindGustInKnots = strongestWindGustInKnots}
        fun HeightOfWindWavesInMeters(heightOfWindWavesInMeters: Float) = apply {this.HeightOfWindWavesInMeters = heightOfWindWavesInMeters}
        fun PeriodForWindWavesInSeconds(periodForWindWavesInSeconds: Float) = apply {this.PeriodForWindWavesInSeconds = periodForWindWavesInSeconds}
        fun HeightForFirstSwellSystemInMeters(heightForFirstSwellSystemInMeters: Float) = apply {this.HeightForFirstSwellSystemInMeters = heightForFirstSwellSystemInMeters}
        fun PeriodForFirstSwellSystemInSeconds(periodForFirstSwellSystemInSeconds: Float) = apply {this.PeriodForFirstSwellSystemInSeconds = periodForFirstSwellSystemInSeconds}
        fun DirectionForFirstSwellSystemInDegrees(directionForFirstSwellSystemInDegrees: Float) = apply {this.DirectionForFirstSwellSystemInDegrees = directionForFirstSwellSystemInDegrees}
        fun ConcentrationOfSeaIce(concentrationOfSeaIce: String) = apply {this.ConcentrationOfSeaIce = concentrationOfSeaIce}
        fun SeaIceStageOfDevelopment(seaIceStageOfDevelopment: String) = apply {this.SeaIceStageOfDevelopment = seaIceStageOfDevelopment}
        fun OceanIce(oceanIce: String) = apply {this.OceanIce = oceanIce}
        fun DirectionToNearestIceEdge(directionToNearestIceEdge: Float) = apply {this.DirectionToNearestIceEdge = directionToNearestIceEdge}
        fun SeaIceConditionsAndDevelopmentTheLastThreeHours(seaIceConditionsAndDevelopmentTheLastThreeHours: String) = apply {this.SeaIceConditionsAndDevelopmentTheLastThreeHours = seaIceConditionsAndDevelopmentTheLastThreeHours}
        fun ReasonForIcing(reasonForIcing: String) = apply {this.ReasonForIcing = reasonForIcing}
        fun IceThicknessInCm(iceThicknessInCm: Float) = apply {this.IceThicknessInCm = iceThicknessInCm}
        fun ChangeInIce(changeInIce: String) = apply {this.ChangeInIce = changeInIce}
    }

    fun GetAsRequestBody(): String {
        val boundaryId = "CINF2jipI367JKKN" // TODO: Generate random id
        val messageTimestamp = System.currentTimeMillis() / 1000
        val messageTimeString = "20220706_01998.debug" // TODO: Generate datetime string from timestamp
        val orapUserName = "" // TODO: Get username from settings
        val orapUserPassword = "" // TODO: Get password from settings
        val timeOfObservation = System.currentTimeMillis() / 1000 // TODO: Get timestamp for observation time (rounded to nearest hour, GMT+0)
        val hiddenMessage = "%1$d".format(10) // TODO:
        val hiddenKlMessage = "" // TODO:
        val hiddenKlStatus = "" // TODO:


        val stringBuilder = StringBuilder();

        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.ACTION, OrapConstants.FormValues.ACTION_SUBMIT_REPORT))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.TAG, messageTimeString))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.REG_EPOC, messageTimestamp))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.USER, orapUserName))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.PASSWORD, orapUserPassword))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.MELDINGSTYPE, OrapConstants.FormValues.REPORT_MESSAGE_TYPE))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_TERMIN, timeOfObservation))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_MESS, ))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_KL_MESS, ))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_KL_STATUS, ))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_FILE, OrapConstants.FormValues.HIDDEN_FILE_VALUE))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.HIDDEN_TYPE, OrapConstants.FormValues.HIDDEN_TYPE))
        stringBuilder.append(GetValueAsWebKitForm(OrapConstants.FormDataNames.LSTEP, OrapConstants.FormValues.LSTEP))

        return "";
    }

    fun GetValueAsWebKitForm(actionName: String, value: String): String {
        return "------WebKitFormBoundaryCINF2jipI367JKKN\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}"
    }

    fun GetValueAsWebKitForm(actionName: String, value: Long): String {
        return "------WebKitFormBoundaryCINF2jipI367JKKN\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}"
    }

    fun GetValueAsWebKitForm(actionName: String, value: Int): String {
            return "------WebKitFormBoundaryCINF2jipI367JKKN\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}"
    }
}