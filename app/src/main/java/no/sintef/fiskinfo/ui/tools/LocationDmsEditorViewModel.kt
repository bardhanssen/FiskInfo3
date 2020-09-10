package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor


class LocationDmsEditorViewModel : LocationEditorViewModel() {
    var format = FORMAT_SECONDS

    val latitudeDegrees = MutableLiveData<Double>()
    val latitudeMinutes = MutableLiveData<Double>()
    val latitudeSeconds = MutableLiveData<Double>()

    val longitudeDegrees = MutableLiveData<Double>()
    val longitudeMinutes = MutableLiveData<Double>()
    val longitudeSeconds = MutableLiveData<Double>()

    var dmsLocation = MutableLiveData<DMSLocation>()

    override fun initWithLocation(location : Location) {
        super.initWithLocation(location)

        if (location != null)
            dmsLocation.value = locationToDms(location)
        /*
        var latArray = splitCoordinate(location.latitude.absoluteValue, format)
        var longArray = splitCoordinate(location.longitude.absoluteValue, format)

        latitudeDegrees.value = latArray[0]
        latitudeMinutes.value = latArray[1]
        latitudeSeconds.value = latArray[2]

        longitudeDegrees.value = longArray[0]
        longitudeMinutes.value = longArray[1]
        longitudeSeconds.value = longArray[2]

        dmsLocation.value = DMSLocation(
            latitudeSouth.value!!, latArray[0], latArray[1], latArray[2],
            longitudeWest.value!!, longArray[0], longArray[1], longArray[2]
        )
        */
   }

    fun validateLocation():Boolean {
        return (dmsLocation != null)
    }

    fun getLocation():Location? {
        if (!validateLocation())
            return null

        return dmsToLocation(dmsLocation.value!!)

//        var loc = Location("")

        // buildCoordinate(doubleArrayOf(latitudeDegrees.value!!, latitudeMinutes.value!!, latitudeSeconds.value!!), latitudeSouth.value!! )
        // buildCoordinate(doubleArrayOf(longitudeDegrees.value!!, longitudeMinutes.value!!, longitudeSeconds.value!!), longitudeWest.value!! )
//        buildCoordinate(doubleArrayOf(dmsLocation.value!!.latitudeDegrees, dmsLocation.value!!.latitudeMinutes, dmsLocation.value!!.latitudeSeconds), dmsLocation.value!!.latitudeSouth)
//        buildCoordinate(doubleArrayOf(dmsLocation.value!!.longitudeDegrees, dmsLocation.value!!.longitudeMinutes, dmsLocation.value!!.longitudeSeconds), longitudeWest.value!! )
//        return loc
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