package no.sintef.fiskinfo.util

import android.content.Context
import android.location.Location
import android.preference.PreferenceManager
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import java.text.SimpleDateFormat
import java.util.*

var sdf = SimpleDateFormat("yyyy-MM-dd")
var stf = SimpleDateFormat("HH:mm")

fun formatDate(date: Date?):String {
    if (date == null)
        return "not set"
    return sdf.format(date)
}

fun formatTime(date: Date?):String {
    if (date == null)
        return "00:00"
    return stf.format(date)
}

val COORDINATE_FORMAT_DMS = "D_M_S"
val COORDINATE_FORMAT_DDM = "D_DM"

val COORDINATE_FORMAT_MAP = mapOf(
    COORDINATE_FORMAT_DMS to Location.FORMAT_SECONDS,
    COORDINATE_FORMAT_DDM to Location.FORMAT_MINUTES
)


fun formatLocation(location : Location, context: Context):String {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val formatCodeStr = prefs.getString("coordinate_format", COORDINATE_FORMAT_DMS)
    if (formatCodeStr == null)
        return location.toString()

    val formatCode = COORDINATE_FORMAT_MAP[formatCodeStr]!!
    val latStr = Location.convert(location.latitude, formatCode)
    val longStr = Location.convert(location.longitude, formatCode)
    return latStr + " " + longStr
}