package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs
import kotlin.math.floor


class LocationDmsViewModel : LocationViewModel() {
    var format = FORMAT_SECONDS
    var dmsLocation = MutableLiveData<DMSLocation>()

    override fun setNewLocation(location : Location) {
        if (location != null)
            dmsLocation.value = locationToDms(location)
    }

    fun validateLocation():Boolean {
        return (dmsLocation != null)
    }

    override fun getLocation():Location? {
        if (!validateLocation())
            return null

        return dmsToLocation(dmsLocation.value!!)
    }

    fun dmsToLocation(dms : DMSLocation):Location? {
        var loc = Location("")
        with(dmsLocation.value!!) {
            loc.latitude = latitudeDegrees + (latitudeMinutes/60.0) + (latitudeSeconds / 3600.0)
            if (latitudeSouth) loc.latitude = -loc.latitude

            loc.longitude = longitudeDegrees + (longitudeMinutes/60.0) + (longitudeSeconds / 3600.0)
            if (longitudeWest) loc.longitude = -loc.longitude
        }
        return loc
    }

    fun locationToDms(location : Location):DMSLocation {
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

    data class DMSLocation(var latitudeSouth : Boolean = false,
                           var latitudeDegrees : Int = 0,
                           var latitudeMinutes : Int = 0,
                           var latitudeSeconds : Double = 0.0,

                           var longitudeWest : Boolean = false,
                           var longitudeDegrees : Int = 0,
                           var longitudeMinutes : Int = 0,
                           var longitudeSeconds : Double = 0.0)

}