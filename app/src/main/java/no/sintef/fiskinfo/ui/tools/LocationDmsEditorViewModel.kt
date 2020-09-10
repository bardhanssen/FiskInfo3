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

   }

    fun validateLocation():Boolean {
        return true
    }

    fun getLocation():Location? {
        if (!validateLocation())
            return null

        var loc = Location("")

        // buildCoordinate(doubleArrayOf(latitudeDegrees.value!!, latitudeMinutes.value!!, latitudeSeconds.value!!), latitudeSouth.value!! )
        // buildCoordinate(doubleArrayOf(longitudeDegrees.value!!, longitudeMinutes.value!!, longitudeSeconds.value!!), longitudeWest.value!! )
        buildCoordinate(doubleArrayOf(dmsLocation.value!!.latitudeDegrees, dmsLocation.value!!.latitudeMinutes, dmsLocation.value!!.latitudeSeconds), dmsLocation.value!!.latitudeSouth)
        buildCoordinate(doubleArrayOf(dmsLocation.value!!.longitudeDegrees, dmsLocation.value!!.longitudeMinutes, dmsLocation.value!!.longitudeSeconds), longitudeWest.value!! )
        return loc
    }


    data class DMSLocation(var latitudeSouth : Boolean,
                           var latitudeDegrees : Double,
                           var latitudeMinutes : Double,
                           var latitudeSeconds : Double,

                           var longitudeWest : Boolean,
                           var longitudeDegrees : Double,
                           var longitudeMinutes : Double,
                           var longitudeSeconds : Double)

}