package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.location.Location.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor


abstract class LocationViewModel : ViewModel() {
    var listPosition = 0;

    open fun initWithLocation(location : Location, index : Int) {
        listPosition = index;
        setNewLocation(location)
   }

    abstract fun setNewLocation(location : Location)
    abstract fun getLocation():Location?

}