package no.sintef.fiskinfo.util

import no.sintef.fiskinfo.model.orap.OrapConstants
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class OrapUtils {
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

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: String): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Long): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
        }

        fun getValueAsWebKitForm(boundaryId: String, actionName: String, value: Int): String {
            return "------WebKitFormBoundary${boundaryId}\nContent-Disposition: form-data; name=\"${actionName}\"\n\n${value}\n"
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