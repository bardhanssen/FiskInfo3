package no.sintef.fiskinfo.util

import android.location.Location
import kotlin.math.abs
import kotlin.math.floor

data class DDMLocation(var latitudeSouth : Boolean = false,
                       var latitudeDegrees : Int = 0,
                       var latitudeDecimalMinutes : Double = 0.0,

                       var longitudeWest : Boolean = false,
                       var longitudeDegrees : Int = 0,
                       var longitudeDecimalMinutes : Double = 0.0) {

    companion object {
       fun fromLocation(location: Location): DDMLocation {
            var ddm = DDMLocation()
            with(ddm) {
                latitudeSouth = location.latitude < 0
                var absCoord = abs(location.latitude)
                latitudeDegrees = floor(absCoord).toInt()
                absCoord -= latitudeDegrees
                latitudeDecimalMinutes = absCoord * 60.0

                longitudeWest = location.longitude < 0
                absCoord = abs(location.longitude)
                longitudeDegrees = floor(absCoord).toInt()
                absCoord -= longitudeDegrees
                absCoord *= 60.0
                longitudeDecimalMinutes = absCoord * 60.0
            }
            return ddm;
        }
    }

    fun toLocation(): Location? {
        var loc = Location("")
        loc.latitude = latitudeDegrees + (latitudeDecimalMinutes/60.0)
        if (latitudeSouth) loc.latitude = -loc.latitude

        loc.longitude = longitudeDegrees + (longitudeDecimalMinutes/60.0)
        if (longitudeWest) loc.longitude = -loc.longitude
        return loc
    }

}
