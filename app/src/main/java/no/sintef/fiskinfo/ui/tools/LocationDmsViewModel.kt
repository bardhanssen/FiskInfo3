package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import no.sintef.fiskinfo.util.DMSLocation
import kotlin.math.abs
import kotlin.math.floor


class LocationDmsViewModel : LocationViewModel() {
    var format = FORMAT_SECONDS
    var dmsLocation = MutableLiveData<DMSLocation>()

    override fun setNewLocation(location : Location) {
        if (location != null)
            dmsLocation.value = DMSLocation.fromLocation(location)
    }

    fun validateLocation():Boolean {
        return (dmsLocation != null)
    }

    override fun getLocation():Location? {
        if (!validateLocation())
            return null

        return dmsLocation.value!!.toLocation()
    }
}