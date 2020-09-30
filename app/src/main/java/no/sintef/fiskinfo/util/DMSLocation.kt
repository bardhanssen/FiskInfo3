package no.sintef.fiskinfo.util

import android.location.Location
import kotlin.math.abs
import kotlin.math.floor

data class DMSLocation(var latitudeSouth : Boolean = false,
                       var latitudeDegrees : Int = 0,
                       var latitudeMinutes : Int = 0,
                       var latitudeSeconds : Double = 0.0,

                       var longitudeWest : Boolean = false,
                       var longitudeDegrees : Int = 0,
                       var longitudeMinutes : Int = 0,
                       var longitudeSeconds : Double = 0.0) {

    companion object {
        fun fromLocation(location : Location):DMSLocation {
            var dms = DMSLocation()
            with (dms) {
                latitudeSouth = location.latitude < 0
                var absCoord = abs(location.latitude)
                latitudeDegrees = floor(absCoord).toInt()
                absCoord -= latitudeDegrees
                absCoord *= 60.0
                latitudeMinutes = floor(absCoord).toInt()
                absCoord -= latitudeMinutes;
                latitudeSeconds = absCoord*60.0;

                longitudeWest = location.longitude < 0
                absCoord = abs(location.longitude)
                longitudeDegrees = floor(absCoord).toInt()
                absCoord -= longitudeDegrees
                absCoord *= 60.0
                longitudeMinutes = floor(absCoord).toInt()
                absCoord -= longitudeMinutes;
                longitudeSeconds = absCoord*60.0;
            }
            return dms;
        }
    }

    fun toLocation(): Location {
        var loc = Location("")

        loc.latitude = latitudeDegrees + (latitudeMinutes/60.0) + (latitudeSeconds / 3600.0)
        if (latitudeSouth) loc.latitude = -loc.latitude

        loc.longitude = longitudeDegrees + (longitudeMinutes/60.0) + (longitudeSeconds / 3600.0)
        if (longitudeWest) loc.longitude = -loc.longitude
        return loc
    }

}
