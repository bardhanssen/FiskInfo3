package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs
import kotlin.math.floor


class LocationDmmViewModel : LocationViewModel() {
    var format = FORMAT_MINUTES
    var dmmLocation = MutableLiveData<DMMLocation>();

    override fun setNewLocation(location : Location) {
        if (location != null)
            dmmLocation.value = locationToDmm(location)
    }

    fun validateLocation():Boolean {
        return (dmmLocation != null)
    }

    override fun getLocation():Location? {
        if (!validateLocation())
            return null

        return dmmToLocation(dmmLocation.value!!)
    }

    fun dmmToLocation(dmm : DMMLocation):Location? {
        var loc = Location("")
        with(dmmLocation.value!!) {
            loc.latitude = latitudeDegrees + (latitudeDecimalMinutes/60.0)
            if (latitudeSouth) loc.latitude = -loc.latitude

            loc.longitude = longitudeDegrees + (longitudeDecimalMinutes/60.0)
            if (longitudeWest) loc.longitude = -loc.longitude
        }
        return loc
    }

    fun locationToDmm(location : Location): DMMLocation {
        var dmm = DMMLocation()
        with (dmm) {
            latitudeSouth = location.latitude < 0
            var absCoord = abs(location.latitude)
            latitudeDegrees = floor(absCoord).toInt()
            absCoord -= latitudeDegrees
            latitudeDecimalMinutes = absCoord *60.0

            longitudeWest = location.longitude < 0
            absCoord = abs(location.longitude)
            longitudeDegrees = floor(absCoord).toInt()
            absCoord -= longitudeDegrees
            absCoord *= 60.0
            longitudeDecimalMinutes = absCoord * 60.0
        }
        return dmm;
    }

    data class DMMLocation(var latitudeSouth : Boolean = false,
                           var latitudeDegrees : Int = 0,
                           var latitudeDecimalMinutes : Double = 0.0,

                           var longitudeWest : Boolean = false,
                           var longitudeDegrees : Int = 0,
                           var longitudeDecimalMinutes : Double = 0.0)

}