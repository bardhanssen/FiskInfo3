package no.sintef.fiskinfo.ui.sprice

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.sprice.*
import no.sintef.fiskinfo.utilities.ui.ObservableAndroidViewModel
import java.time.Instant
import java.util.*

class ReportIcingViewModel(application: Application) : ObservableAndroidViewModel(application) {
    private val _reportingTime = MutableStateFlow(Date.from(Instant.now()))
    val reportingTime: MutableStateFlow<Date> = _reportingTime

    val observationTime = MutableLiveData<Date>()
    val location = MutableLiveData<Location>()
    var reportChecked = MutableLiveData<Boolean>()
    var reportValid = MutableLiveData<Boolean>()
    val maxMiddleWindTime = MutableLiveData<MaxMiddleWindTimeEnum>()

    private val _vesselIcingThickness = MutableStateFlow("")
    private val _airTemperature = MutableStateFlow("")
    private val _seaTemperature = MutableStateFlow("")

    private val _synopTimeSelect = MutableStateFlow("00:00")
    val synopTimeSelect: MutableStateFlow<String> = _synopTimeSelect

    val vesselIcingThickness: MutableStateFlow<String> = _vesselIcingThickness
    val airTemperature: MutableStateFlow<String> = _airTemperature
    val seaTemperature: MutableStateFlow<String> = _seaTemperature

    fun init() {
        reportingTime.value = Date()
        synopTimeSelect.value = "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:00"
        maxMiddleWindTime.value = MaxMiddleWindTimeEnum.DURING_OBSERVATION
        val defaultLoc = Location("")
        // TODO: Default locations
        defaultLoc.latitude = 0.0
        defaultLoc.longitude = 0.0
        location.value = defaultLoc
        reportChecked.value = false
        reportValid.value = false

        viewModelScope.launch {
            _airTemperature.value = ""
            _seaTemperature.value = ""
        }
    }

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
            .airTemperature(_airTemperature.value)
            .seaTemperature(_seaTemperature.value)
            .maxMiddleWindTime(maxMiddleWindTime.value!!)
            .iceThicknessInCm(vesselIcingThickness.value)
            .build()
    }

    fun setReportingDate(date: Date) {
        // TODO: pick out only date part (not time)
        val c = Calendar.getInstance()
        c.time = reportingTime.value

        val newDateC = Calendar.getInstance()
        newDateC.time = date
        c.set(Calendar.YEAR, newDateC.get(Calendar.YEAR))
        c.set(Calendar.DAY_OF_YEAR, newDateC.get(Calendar.DAY_OF_YEAR))

        reportingTime.value = c.time
    }

    fun setReportingTime(hourOfDay: Int, minutes: Int) {
        val c = Calendar.getInstance()
        c.time = reportingTime.value
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minutes)
        reportingTime.value = c.time
    }

    val maxMiddleWindTimeName: LiveData<String> = Transformations.map(maxMiddleWindTime) { code ->
        code.getLocalizedName(getApplication())
    }
}