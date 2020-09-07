package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor


open class LocationEditorViewModel : ViewModel() {
    val latitudeSouth = MutableLiveData<Boolean>()
    val longitudeWest = MutableLiveData<Boolean>()

    open fun initWithLocation(location : Location) {
        latitudeSouth.value = location.latitude < 0
        longitudeWest.value = location.longitude < 0
   }

    fun splitCoordinate(coordinate: Double, outputType: Int): DoubleArray {
        if (outputType == FORMAT_DEGREES)
            return doubleArrayOf(coordinate)

        var sign = if (coordinate < 0) -1.0 else 1.0
        var absCoord = abs(coordinate)

        var degrees = floor(absCoord).toInt()
        absCoord -= degrees.toDouble()
        absCoord *= 60.0
        if (outputType == FORMAT_MINUTES)
            return doubleArrayOf(sign * degrees, absCoord);

        val minutes = floor(absCoord).toInt()
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