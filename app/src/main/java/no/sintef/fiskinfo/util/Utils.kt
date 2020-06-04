package no.sintef.fiskinfo.util

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