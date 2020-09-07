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
   }

    fun validateLocation():Boolean {
        return true
    }

    fun getLocation():Location? {
        if (!validateLocation())
            return null

        var loc = Location("")

        buildCoordinate(doubleArrayOf(latitudeDegrees.value!!, latitudeMinutes.value!!, latitudeSeconds.value!!), latitudeSouth.value!! )
        buildCoordinate(doubleArrayOf(longitudeDegrees.value!!, longitudeMinutes.value!!, longitudeSeconds.value!!), longitudeWest.value!! )
        return loc
    }

}