package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs
import kotlin.math.floor


class LocationDdmViewModel : LocationViewModel() {
    var format = FORMAT_MINUTES
    var ddmLocation = MutableLiveData<DDMLocation>();

    override fun setNewLocation(location : Location) {
        if (location != null)
            ddmLocation.value = locationToDdm(location)
    }

    fun validateLocation():Boolean {
        return (ddmLocation != null)
    }

    override fun getLocation():Location? {
        if (!validateLocation())
            return null

        return dmmToLocation(ddmLocation.value!!)
    }

    fun dmmToLocation(dmm : DDMLocation):Location? {
        var loc = Location("")
        with (ddmLocation.value!!) {
            loc.latitude = latitudeDegrees + (latitudeDecimalMinutes/60.0)
            if (latitudeSouth) loc.latitude = -loc.latitude

            loc.longitude = longitudeDegrees + (longitudeDecimalMinutes/60.0)
            if (longitudeWest) loc.longitude = -loc.longitude
        }
        return loc
    }

    fun locationToDdm(location : Location): DDMLocation {
        var ddm = DDMLocation()
        with (ddm) {
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
        return ddm;
    }

    data class DDMLocation(var latitudeSouth : Boolean = false,
                           var latitudeDegrees : Int = 0,
                           var latitudeDecimalMinutes : Double = 0.0,

                           var longitudeWest : Boolean = false,
                           var longitudeDegrees : Int = 0,
                           var longitudeDecimalMinutes : Double = 0.0)

}