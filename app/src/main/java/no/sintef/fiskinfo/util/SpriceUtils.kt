package no.sintef.fiskinfo.util

import no.sintef.fiskinfo.model.sprice.OrapConstants
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SpriceUtils {
    companion object {
        /**
         * Generate an Orap Message tag from the given [datetime] and [username].
         * The returned tag will be in the format 'YYYYMMDD_[username].debug'.
         *
         */
        fun getOrapMessageTag(datetime: LocalDateTime, username: String): String {
            val formatter = DateTimeFormatter.ofPattern(OrapConstants.ACTION_TAG_DATE_FORMAT)

            return "${datetime.format(formatter)}_${username}.debug"
        }

        fun getBoundaryIdString(length: Int = OrapConstants.BOUNDARY_ID_LENGTH) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

        fun getFormattedTimeStamp(datetime: LocalDateTime, format: String): String {
            val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault());

            return datetime.format(formatter)

        }

        fun getFormattedHiddenMessage(username: String, heightOfWindWavesInMeters: String, periodForWindWavesInSeconds: String, iceThicknessInCm: Int, latitude: String, longitude: String, airTemperature: String): String {
            return "012345678      ${username},17,,,,,,,,,,,${heightOfWindWavesInMeters},${periodForWindWavesInSeconds},,,,,,,,,,,,,,,${iceThicknessInCm},,${latitude},${longitude},${airTemperature},,,,,,\n\n"
        }

        fun getFormattedHiddenKlMessage(username: String, messageReceivedTime: LocalDateTime, synop: LocalDateTime, heightOfWindWavesInMeters: String, periodForWindWavesInSeconds: String, iceThicknessInCm: Int, latitude: String, longitude: String, airTemperature: String, reportingTimeEpoch: Long): String {
            return "kldata/nationalnr=${username}/type=317/test/received_time=\"${
                getFormattedTimeStamp(
                    messageReceivedTime,
                    OrapConstants.HIDDEN_KL_MESSAGE_RECEIVED_TIME_FORMAT
                )
            }\"\n" +
                    "IX,WW,VV,HL,NN,NH,CL,CM,CH,W1,W2,HW,PW,DW1,PW1,HW1,DW2,PW2,HW2,DD,FF,CI,SI,BI,DI,ZI,XIS,ES,ERS,MLAT,MLON,TA,UU,UH,PR,PO,PP,AA,MDIR,MSPEED\n" +
                    "${
                        getFormattedTimeStamp(
                            synop,
                            OrapConstants.HIDDEN_KL_MESSAGE_OBSERVATION_TIMESTAMP
                        )
                    },3,,,,,,,,,,,${heightOfWindWavesInMeters},${periodForWindWavesInSeconds},,,,,,,,,,,,,,,${iceThicknessInCm},,${latitude},${longitude},${airTemperature},-6,,,,,,,\n" +
                    "Orap_smsformat_input ${reportingTimeEpoch} ${username},17,,,,,,,,,,,${heightOfWindWavesInMeters},${periodForWindWavesInSeconds},,,,,,,,,,,,,,,${iceThicknessInCm},,${latitude},${longitude},${airTemperature},,,,,,\n" +
                    "Local_kvalobs_data /var/www/orap//orap_data//xenial-test//317/1/${username}/orap_${
                        getFormattedTimeStamp(
                            messageReceivedTime,
                            OrapConstants.HIDDEN_KL_MESSAGE_RECEIVED_FILE_NAME_TIMESTAMP
                        )
                    }.txt;"
        }

        fun getFormattedHiddenKlStatus(observationEpoch: Long, Username: String, heightOfWindWavesInMeters: String, periodForWindWavesInSeconds: String, iceThicknessInCm: Int, latitude: String, longitude: String, airTemperature: String): String {
            return "${observationEpoch * 10} || 012345678      ${Username},17,,,,,,,,,,,${heightOfWindWavesInMeters},${periodForWindWavesInSeconds},,,,,,,,,,,,,,,${iceThicknessInCm},,${latitude},${longitude},${airTemperature},,,,,,\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: String): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Long): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Long?): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value ?: ""}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Int): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Int?): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value ?: ""}\n"
        }

        fun getWebFormKitEndTag(boundaryId: String): String {
            return "------WebKitFormBoundary${boundaryId}--"
        }
    }

    class WebFormBuilder(boundaryId: String, entries: MutableMap<String, String> = mutableMapOf()) {
        val BoundaryId: String
        val Entries: MutableMap<String, String>

        init {
            BoundaryId = boundaryId
            Entries = entries
        }

        fun addFormValue(key: String, value: String) {
            Entries[key] = value
        }

        fun getWebFormString(): String {
            val stringBuilder = StringBuilder()

            for ((key, value) in Entries) {
                stringBuilder.append(getValueAsWebKitForm(BoundaryId, key, value))
            }

            stringBuilder.append("------WebKitFormBoundary${BoundaryId}--")

            return stringBuilder.toString()
        }
    }
}