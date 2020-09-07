package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor


class LocationDmmEditorViewModel : LocationEditorViewModel() {
    var format = FORMAT_MINUTES

    val latitudeDegrees = MutableLiveData<Double>()
    val latitudeDecimalMinutes = MutableLiveData<Double>()

    val longitudeDegrees = MutableLiveData<Double>()
    val longitudeDecimalMinutes = MutableLiveData<Double>()

    override fun initWithLocation(location : Location) {
        super.initWithLocation(location)
        latitudeSouth.value = location.latitude < 0
        longitudeWest.value = location.longitude < 0

        var latArray = splitCoordinate(location.latitude.absoluteValue, format)
        var longArray = splitCoordinate(location.longitude.absoluteValue, format)

        latitudeDegrees.value = latArray[0]
        latitudeDecimalMinutes.value = latArray[1]

        longitudeDegrees.value = longArray[0]
        longitudeDecimalMinutes.value = longArray[1]
   }

    fun validateLocation():Boolean {
        return true
    }

    fun getLocation():Location? {
        if (!validateLocation())
            return null

        var loc = Location("")
        buildCoordinate(doubleArrayOf(latitudeDegrees.value!!, latitudeDecimalMinutes.value!!), latitudeSouth.value!! )
        buildCoordinate(doubleArrayOf(longitudeDegrees.value!!, longitudeDecimalMinutes.value!!), longitudeWest.value!! )
        return loc
    }


}