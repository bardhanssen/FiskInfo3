package no.sintef.fiskinfo.ui.sprice

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.sprice.*
import no.sintef.fiskinfo.utilities.ui.ObservableAndroidViewModel
import java.util.*

class ReportIcingViewModel(application: Application) : ObservableAndroidViewModel(application) {
    val reportingTime = MutableLiveData<Date>()
    val observationTime = MutableLiveData<Date>()
    val synopTimeSelect = MutableLiveData<IcingReportHourEnum>()
    val location = MutableLiveData<Location>()
    var reportChecked = MutableLiveData<Boolean>()
    var reportValid = MutableLiveData<Boolean>()
    val maxMiddleWindTime = MutableLiveData<MaxMiddleWindTimeEnum>()
    private val _airTemperature = MutableStateFlow(0F)
    private val _seaTemperature = MutableStateFlow(0F)
    val vesselIcingThickness: MutableStateFlow<Int> = MutableStateFlow(0)

    val airTemperature: StateFlow<Float> = _airTemperature
    val seaTemperature: StateFlow<Float> = _seaTemperature


    internal fun getIcingReportBody(): ReportIcingRequestBody {
        val context: Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val orapUsername = prefs.getString(context.getString(R.string.pref_sprice_username_key), "") ?: ""
        val orapPassword = prefs.getString(context.getString(R.string.pref_sprice_password_key), "") ?: ""
        val callSign = prefs.getString(context.getString(R.string.pref_sprice_call_sign_key), "") ?: ""

        return ReportIcingRequestBody.Builder() // TODO: Populate values
            .username(orapUsername)
            .password(orapPassword)
            .callSign(callSign)
            .latitude(location.value?.latitude.toString())
            .longitude(location.value?.longitude.toString())
            .airTemperature(_airTemperature.value.toString())
            .seaTemperature(_seaTemperature.value.toString())
            .maxMiddleWindTime(maxMiddleWindTime.value!!)
            .iceThicknessInCm(vesselIcingThickness.value)
            .build()
    }

    fun initContent() {
        reportingTime.value = Date()
        val defaultLoc = Location("")
        // TODO: Default locations
        defaultLoc.latitude = 0.0
        defaultLoc.longitude = 0.0
        location.value = defaultLoc
        reportChecked.value = false
        reportValid.value = false
        _airTemperature.value = 0F
        _seaTemperature.value = 0F
    }

    fun setReportingDate(date: Date) {
        // TODO: pick out only date part (not time)
        val c = Calendar.getInstance()
        c.time = reportingTime.value!!

        val newDateC = Calendar.getInstance()
        newDateC.time = date
        c.set(Calendar.YEAR, newDateC.get(Calendar.YEAR))
        c.set(Calendar.DAY_OF_YEAR, newDateC.get(Calendar.DAY_OF_YEAR))

        reportingTime.value = c.time
    }

    fun setReportingTime(hourOfDay: Int, minutes: Int) {
        val c = Calendar.getInstance()
        c.time = reportingTime.value!!
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minutes)
        reportingTime.value = c.time
    }

    val maxMiddleWindTimeName: LiveData<String> = Transformations.map(maxMiddleWindTime) { code ->
        code.getLocalizedName(getApplication())
    }
}