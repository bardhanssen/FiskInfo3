package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.absoluteValue


class LocationEditorViewModel : ViewModel() {
    var format = FORMAT_SECONDS // TODO: Get from preferences

    val latitudeSouth = MutableLiveData<Boolean>()
    val latitudePrimary = MutableLiveData<Double>()
    val latitudeSecondary = MutableLiveData<Double>()
    val latitudeTertiary = MutableLiveData<Double>()

    val longitudeWest = MutableLiveData<Boolean>()
    val longitudePrimary = MutableLiveData<Double>()
    val longitudeSecondary = MutableLiveData<Double>()
    val longitudeTertiary = MutableLiveData<Double>()

    fun initWithLocation(location : Location) {
        latitudeSouth.value = location.latitude < 0
        longitudeWest.value = location.longitude < 0

        var latArray = splitCoordinate(location.latitude.absoluteValue, format)
        var longArray = splitCoordinate(location.longitude.absoluteValue, format)

        latitudePrimary.value = latArray[0]
        longitudePrimary.value = longArray[0]

        if (format == FORMAT_MINUTES || format == FORMAT_SECONDS) {
            latitudeSecondary.value = latArray[1]
            longitudeSecondary.value = longArray[1]

            if (format == FORMAT_SECONDS) {
                latitudeTertiary.value = latArray[2]
                longitudeTertiary.value = latArray[3]
            } else {
                latitudeTertiary.value = 0.0
                longitudeTertiary.value = 0.0
            }
        }
   }

    fun validateLocation():Boolean {
        return true
    }

    fun getLocation():Location? {
        if (!validateLocation())
            return null

        var loc = Location("")

        loc.latitude = when (format) {
            FORMAT_DEGREES -> buildCoordinate(doubleArrayOf(latitudePrimary.value!!), latitudeSouth.value!! )
            FORMAT_MINUTES -> buildCoordinate(doubleArrayOf(latitudePrimary.value!!, latitudeSecondary.value!!), latitudeSouth.value!! )
            FORMAT_SECONDS -> buildCoordinate(doubleArrayOf(latitudePrimary.value!!, latitudeSecondary.value!!, latitudeTertiary.value!!), latitudeSouth.value!! )
            else -> 0.0
        }

        loc.longitude = when (format) {
            FORMAT_DEGREES -> buildCoordinate(doubleArrayOf(longitudePrimary.value!!), longitudeWest.value!! )
            FORMAT_MINUTES -> buildCoordinate(doubleArrayOf(longitudePrimary.value!!, longitudeSecondary.value!!), longitudeWest.value!! )
            FORMAT_SECONDS -> buildCoordinate(doubleArrayOf(longitudePrimary.value!!, longitudeSecondary.value!!, longitudeTertiary.value!!), longitudeWest.value!! )
            else -> 0.0
        }
        return loc
    }

    fun splitCoordinate(coordinate: Double, outputType: Int): DoubleArray {
        if (outputType == FORMAT_DEGREES)
            return doubleArrayOf(coordinate)

        var sign = if (coordinate < 0) -1.0 else 1.0
        var absCoord = abs(coordinate)

        var degrees = Math.floor(coordinate).toInt()
        absCoord -= degrees.toDouble()
        absCoord *= 60.0
        if (outputType == FORMAT_MINUTES)
            return doubleArrayOf(sign * degrees, absCoord);

        val minutes = Math.floor(coordinate).toInt()
        absCoord -= minutes.toDouble()
        absCoord *= 60.0

        return doubleArrayOf(sign * degrees.toDouble(), minutes.toDouble(), absCoord)
    }

    fun buildCoordinate(doubleArray: DoubleArray, negate : Boolean):Double {
        var coord = doubleArray[0]
        if (doubleArray.size > 1) {
            coord += (doubleArray[1] / 60.0)
            if (doubleArray.size > 2)
                coord += (doubleArray[1] / 3600.0)
        }
        return if (negate) -coord else coord
    }


}