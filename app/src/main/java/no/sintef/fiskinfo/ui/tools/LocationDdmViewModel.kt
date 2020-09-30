package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import no.sintef.fiskinfo.util.DDMLocation
import kotlin.math.abs
import kotlin.math.floor


class LocationDdmViewModel : LocationViewModel() {
    var format = FORMAT_MINUTES
    var ddmLocation = MutableLiveData<DDMLocation>();

    override fun setNewLocation(location : Location) {
        if (location != null)
            ddmLocation.value = DDMLocation.fromLocation(location)
    }

    fun validateLocation():Boolean {
        return (ddmLocation != null)
    }

    override fun getLocation():Location? {
        if (!validateLocation())
            return null

        return ddmLocation.value!!.toLocation()
    }
}