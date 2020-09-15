package no.sintef.fiskinfo.ui.tools

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import no.sintef.fiskinfo.model.fishingfacility.DeploymentInfo
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import no.sintef.fiskinfo.util.locationsToWTK
import no.sintef.fiskinfo.utilities.ui.ObservableAndroidViewModel
import java.util.*

class DeploymentViewModel(application: Application) : ObservableAndroidViewModel(application)  {

    //private val deploymentInfo = MutableLiveData<DeploymentInfo>()
    //private val toolTypeCodeName = MutableLiveData<String>() // Name is separate propery to support localization
    val toolTypeCode = MutableLiveData<ToolTypeCode>()
    val setupTime = MutableLiveData<Date>()
    val comment = MutableLiveData<String>()
    val locations = MutableLiveData<MutableList<Location>>()

    //val getDeploymentInfo: LiveData<DeploymentInfo>
    //    get() = deploymentInfo

    private fun createDeploymentInfo():DeploymentInfo {
        val fishingFacilityRepository = FishingFacilityRepository.getInstance(this.getApplication())
        val profile = fishingFacilityRepository.getFiskInfoProfileDTO()
        // TODO: Add validation of the profile somewhere
        val fiskInfoProfile = profile.value?.fiskinfoProfile!!

        val geometryStr = locationsToWTK(locations.value!!)
        val info = DeploymentInfo(setupTime = setupTime.value!!,
            ircs =  fiskInfoProfile.ircs,
            contactPersonEmail = "",
            contactPersonName = "",
            contactPersonPhone = "",
            toolTypeCode = toolTypeCode.value!!,
            comment = comment.value!!,
            geometryWKT = geometryStr)
        return info;

        //val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
        //info.toolTypeCode = ToolTypeCode.valueOf(prefs.getString("default_tool_type", ToolTypeCode.NETS.code))
        //deploymentInfo.value = info
    }

    fun clearInfo() {
        comment.value = ""
        setupTime.value = Date()
        toolTypeCode.value = ToolTypeCode.NETS
        val defaultLoc = Location("")
        defaultLoc.latitude = 68.333332  //your coords of course
        defaultLoc.longitude = 14.666664
        locations.value = mutableListOf(defaultLoc)
    }


    fun isProfileValid():Boolean {
        val fishingFacilityRepository = FishingFacilityRepository.getInstance(this.getApplication())
        val profile = fishingFacilityRepository.getFiskInfoProfileDTO()
        val fiskInfoProfile = profile.value?.fiskinfoProfile
        if (fiskInfoProfile == null)
            return false
        return (fiskInfoProfile.ircs != null)
        // TODO: Add check on contact person etc.
   }

    fun canSendReport():Boolean {
        // TODO: Check that all required fields of report are present
        return isProfileValid()
    }

    fun sendReport() {
        if (canSendReport()) {
            val info = createDeploymentInfo()
            FishingFacilityRepository.getInstance(getApplication()).sendDeploymentInfo(info)
            // TODO: How to handle feedback. Use a kind of notification object with Livedata? C
            // Could also use error mechanism in views similar to:
            // https://www.journaldev.com/22561/android-mvvm-livedata-data-binding
        }
    }
/*
    fun setToolTypeCode(code: ToolTypeCode) {
        if ((deploymentInfo.value?.toolTypeCode != code) && (code != null)) {
            deploymentInfo.value?.toolTypeCode != code
            selectedToolTypeCodeName.value = code.getLocalizedName(getApplication())
        }
    }
*/
    fun setSetupDate(date : Date) {
        if (date != null) {
            // TODO: pick out only date part (not time)
            val c = Calendar.getInstance()
            c.time = setupTime.value

            val newDateC = Calendar.getInstance()
            newDateC.time = date
            c.set(Calendar.YEAR, newDateC.get(Calendar.YEAR))
            c.set(Calendar.DAY_OF_YEAR, newDateC.get(Calendar.DAY_OF_YEAR))

            setupTime.value = c.time
        }
    }

    fun setSetupTime(hourOfDay : Int, minutes : Int) {
        val c = Calendar.getInstance()
        c.time = setupTime.value
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minutes)
        setupTime.value = c.time
    }

    fun addLocation() {
        val newLoc = Location("")
        newLoc.latitude = 68.223332  //your coords of course
        newLoc.longitude = 13.666664
        locations.value?.add(newLoc)
        locations.postValue(locations.value)
    }

    fun removeLastLocation() {
        if (locations.value?.size!! > 1) {
            locations.value?.removeAt(locations.value!!.size-1)
            locations.postValue(locations.value)
        }
    }


    val toolTypeCodeName: LiveData<String> = Transformations.map(toolTypeCode) {
            code ->  code.getLocalizedName(getApplication())
    }

    //val toolTypeCodeName: LiveData<String>
    //    get() = this.selectedToolTypeCodeName

}
