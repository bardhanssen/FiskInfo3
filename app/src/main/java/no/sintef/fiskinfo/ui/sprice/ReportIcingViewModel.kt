package no.sintef.fiskinfo.ui.sprice

import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
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
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class ReportIcingViewModel(application: Application) : ObservableAndroidViewModel(application) {
    private val _reportingTime = MutableStateFlow(Date.from(Instant.now()))
    private val _synopTimeSelect = MutableStateFlow("00:00")
    private val _airTemperature = MutableStateFlow("")
    private val _seaTemperature = MutableStateFlow("")
    private val _reasonForVesselIcing = MutableStateFlow("")
    private val _vesselIcingThickness = MutableStateFlow("")
    private val _vesselIcingChangeInIcing = MutableStateFlow("")

    val synopDate = MutableLiveData<Date>()
    val location = MutableLiveData<Location>()
    val maxMiddleWindTime = MutableLiveData<MaxMiddleWindTimeEnum>()

    val reportingTime: MutableStateFlow<Date> = _reportingTime
    val synopHourSelect: MutableStateFlow<String> = _synopTimeSelect

    val airTemperature: MutableStateFlow<String> = _airTemperature
    val seaTemperature: MutableStateFlow<String> = _seaTemperature

    val reasonForVesselIcing: MutableStateFlow<String> = _reasonForVesselIcing
    val vesselIcingThickness: MutableStateFlow<String> = _vesselIcingThickness
    val vesselIcingChangeInIcing: MutableStateFlow<String> = _vesselIcingChangeInIcing

    fun init() {
        reportingTime.value = Date.from(Instant.now())
        synopHourSelect.value = "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:00"

        val calendar = Calendar.getInstance()
        calendar.time = Date.from(Instant.now())
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        synopDate.value = calendar.time

        maxMiddleWindTime.value = MaxMiddleWindTimeEnum.DURING_OBSERVATION
        val defaultLoc = Location("")
        defaultLoc.latitude = 0.0
        defaultLoc.longitude = 0.0

        location.value = defaultLoc

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

        return ReportIcingRequestBody.Builder()
            .username(orapUsername)
            .password(orapPassword)
            .callSign(callSign)
            .reportingTime(ZonedDateTime.ofInstant(_reportingTime.value.toInstant(), ZoneId.systemDefault()))
            .synop(ZonedDateTime.ofInstant(synopDate.value!!.toInstant(), ZoneId.systemDefault()))
            .latitude(location.value?.latitude.toString())
            .longitude(location.value?.longitude.toString())
            .airTemperature(_airTemperature.value)
            .seaTemperature(_seaTemperature.value)
            .maxMiddleWindTime(maxMiddleWindTime.value!!)
            .iceThicknessInCm(vesselIcingThickness.value)
            .reasonForIcing(reasonForVesselIcing.value)
            .changeInIce(vesselIcingChangeInIcing.value)
            .build()
    }

    fun setObservationDate(date: Date) {
        val synopCalendar = Calendar.getInstance()
        synopCalendar.time = synopDate.value!!

        val newDateCalendar = Calendar.getInstance()
        newDateCalendar.time = date
        synopCalendar.set(Calendar.YEAR, newDateCalendar.get(Calendar.YEAR))
        synopCalendar.set(Calendar.MONTH, newDateCalendar.get(Calendar.MONTH))
        synopCalendar.set(Calendar.DAY_OF_YEAR, newDateCalendar.get(Calendar.DAY_OF_YEAR))

        Log.e("setReportingTime", "Observation time updated, old: ${synopDate.value}, new:${synopCalendar.time}")
//        _observationTime.value = c.time
        synopDate.value = synopCalendar.time
    }

    fun getSynopHourAsInt(): Int {
        var retval = 0

        try {
            retval = Integer.parseInt(synopHourSelect.value.substring(0, 2))
        } catch(e: NumberFormatException) {

        }

        return retval
    }

    val maxMiddleWindTimeName: LiveData<String> = Transformations.map(maxMiddleWindTime) { code ->
        code.getLocalizedName(getApplication())
    }
}