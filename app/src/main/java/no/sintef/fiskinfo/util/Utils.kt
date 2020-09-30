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

    if (formatCodeStr == COORDINATE_FORMAT_DDM) {
        var ddm = DDMLocation.fromLocation(location)
        val latStr = "${ddm.latitudeDegrees}\u00B0" + "%.4f".format(ddm.latitudeDecimalMinutes) + "\'${if (ddm.latitudeSouth) "S" else "N"}"
        val longStr = "${ddm.longitudeDegrees}\u00B0" + "%.4f".format(ddm.latitudeDecimalMinutes) + "\'${if (ddm.longitudeWest) "W" else "E"}"
        return "$latStr $longStr"
    } else {
        var dms = DMSLocation.fromLocation(location)
        val latStr = "${dms.latitudeDegrees}\u00B0${dms.latitudeMinutes}\'" + "%.2f".format(dms.latitudeSeconds) + "\"${if (dms.latitudeSouth) "S" else "N"}"
        val longStr = "${dms.longitudeDegrees}\u00B0${dms.longitudeMinutes}\'" + "%.2f".format(dms.longitudeSeconds) + "\"${if (dms.longitudeWest) "W" else "E"}"
        return "$latStr $longStr"
    }
/*
    if (formatCodeStr == null)
        return location.toString()

    val formatCode = COORDINATE_FORMAT_MAP[formatCodeStr]!!
    val latStr = Location.convert(location.latitude, formatCode)
    val longStr = Location.convert(location.longitude, formatCode)
    return latStr + " " + longStr*/
}

// Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)

fun locationsToWTK(locations : List<Location>):String {
    if (locations.size == 0) {
        return ""
    } else if (locations.size == 1) {
        return "POINT(${locations[0].latitude} ${locations[0].longitude})"
    } else {
        var result : String = "LINESTRING("
        var first = true
        locations.forEach( {
            if (!first)
                result += ","
            result += it.latitude.toString() + " " + it.longitude.toString()
            first = false
        })
        result += ")"
        return result
    }
}

fun wktToLocations(wkt : String?):List<Location> {
    var result = ArrayList<Location>()
    if (wkt != null) {
        try {
            var numString = wkt.substring( wkt.indexOf("(")+1, wkt.lastIndexOf(")"))
//                .replace("\\(", "")
//                .replace("\\)", "")
            var coordinateStrs = numString.split("[, ]".toRegex())
            for (i in coordinateStrs.indices step 2) {
                val loc = Location("")
                loc.latitude = coordinateStrs[i].toDouble()
                loc.longitude = coordinateStrs[i+1].toDouble()
                result.add(loc)
            }
        } catch (e: Exception) {
        }
    }
    return result
}