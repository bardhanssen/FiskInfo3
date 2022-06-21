package no.sintef.fiskinfo.ui.sprice

import android.app.Application
import android.content.Context
import android.location.Location
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.FiskInfoProfileDTO
import no.sintef.fiskinfo.model.sprice.IcingReport
import no.sintef.fiskinfo.model.sprice.IcingTypeCode
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import no.sintef.fiskinfo.util.locationsToGeoJsonGeometry
import no.sintef.fiskinfo.utilities.ui.ObservableAndroidViewModel
import java.util.*

class ReportIcingViewModel(application: Application) : ObservableAndroidViewModel(application) {
    val reportingTime = MutableLiveData<Date>()
    val locations = MutableLiveData<MutableList<Location>>()
    val icingTypeCode = MutableLiveData<IcingTypeCode>()

    var contactPersonEmail: String? = null
    var contactPersonPhone: String? = null
    private var fiskInfoProfileDTO: LiveData<FiskInfoProfileDTO>? = null
    var initialized = false

    private fun createIcingReport(): IcingReport {
        val profile = getFiskInfoProfileDTO()
        val fiskInfoProfile = profile?.value?.fiskinfoProfile!!

        getContactInfoFromPreferences()

        return IcingReport(
            geometry = locationsToGeoJsonGeometry(locations.value!!),
            reportingTime = reportingTime.value!!

        )
    }

    fun initContent() {
        if (!initialized) {
            var context: Context = getApplication()
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)


            reportingTime.value = Date()
            val defaultLoc = Location("")
            // TODO: Default locations
            defaultLoc.latitude = 0.0  //your coords of course
            defaultLoc.longitude = 0.0
            locations.value = mutableListOf(defaultLoc)
        }
    }

    fun getContactInfoFromPreferences() {
        var context: Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        contactPersonEmail =
            prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        contactPersonPhone =
            prefs.getString(context.getString(R.string.pref_contact_person_phone), "")
    }

    fun getFiskInfoProfileDTO(): LiveData<FiskInfoProfileDTO>? {
        if (fiskInfoProfileDTO == null) {
            val fishingFacilityRepository =
                FishingFacilityRepository.getInstance(this.getApplication())
            fiskInfoProfileDTO = fishingFacilityRepository.getFiskInfoProfileDTO()
        }
        return fiskInfoProfileDTO
    }

    fun setReportingDate(date: Date) {
        if (date != null) {
            // TODO: pick out only date part (not time)
            val c = Calendar.getInstance()
            c.time = reportingTime.value

            val newDateC = Calendar.getInstance()
            newDateC.time = date
            c.set(Calendar.YEAR, newDateC.get(Calendar.YEAR))
            c.set(Calendar.DAY_OF_YEAR, newDateC.get(Calendar.DAY_OF_YEAR))

            reportingTime.value = c.time
        }
    }

    fun setReportingTime(hourOfDay: Int, minutes: Int) {
        val c = Calendar.getInstance()
        c.time = reportingTime.value
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minutes)
        reportingTime.value = c.time
    }

    fun clear() {
        initialized = false
    }

    val icingTypeCodeName: LiveData<String> = Transformations.map(icingTypeCode) { code ->
        code.getLocalizedName(getApplication())
    }

}