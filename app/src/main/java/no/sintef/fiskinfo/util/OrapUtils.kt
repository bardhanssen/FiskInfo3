package no.sintef.fiskinfo.util

import no.sintef.fiskinfo.model.orap.OrapConstants
import java.text.SimpleDateFormat
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
        fun GetOrapMessageTag(datetime: LocalDateTime, username: String): String {
            val formatter = DateTimeFormatter.ofPattern(OrapConstants.ACTION_TAG_DATE_FORMAT)

            return "${datetime.format(formatter)}_${username}.debug"
        }

        fun GetBoundaryIdString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

        fun GetFormattedTimeStamp(datetime: LocalDateTime, format: String): String {
            val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault());

            return datetime.format(formatter)

        }
    }
}