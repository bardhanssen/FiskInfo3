package no.sintef.fiskinfo.dal.sprice

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class ZonedDateTimeConverter {
    @TypeConverter
    fun toDate(timeStamp: String): ZonedDateTime {
        return ZonedDateTime.parse(timeStamp)
    }

    @TypeConverter
    fun toTimestampString(datetime: ZonedDateTime): String {
        return datetime.toString()
    }
}