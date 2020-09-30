package no.sintef.fiskinfo.ui.tools

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.*
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import java.util.*

class ToolsViewModel(application: Application) : AndroidViewModel(application)  {

    private val selectedTool = MutableLiveData<FishingFacility?>()
    private var confirmedTools: LiveData<List<FishingFacility>>? = null
    private var unconfirmedTools: LiveData<List<FishingFacility>>? = null

    private var profile: LiveData<FiskInfoProfileDTO>? = null

    private val draftReport = MutableLiveData<Report>()
    private val selectedToolTypeCodeName = MutableLiveData<String>()

    fun selectTool(tool: FishingFacility?, isConfirmed: Boolean) {
        //selectedIsIncomming.value = isConfirmed
        selectedTool.value = tool
        selectedToolTypeCodeName.value = tool?.toolTypeCode?.getLocalizedName(getApplication())
    }

    fun getSelectedTool(): LiveData<FishingFacility?> {
        return selectedTool
    }


    fun getConfirmedTools(): LiveData<List<FishingFacility>>? {
        if (confirmedTools == null) {
            confirmedTools = FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools()
        }
        return confirmedTools
    }

    fun getUnconfirmedTools(): LiveData<List<FishingFacility>>? {
        if (unconfirmedTools == null) {
            unconfirmedTools = FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools()
        }
        return unconfirmedTools
    }

    fun refreshTools() {
        FishingFacilityRepository.getInstance(getApplication()).refreshFishingFacilityChanges();
    }

    fun getProfile(): LiveData<FiskInfoProfileDTO>? {
        if (profile == null) {
            profile = FishingFacilityRepository.getInstance(getApplication()).getFiskInfoProfileDTO()
        }
        return profile;
    }

    val draft: LiveData<Report>
        get() = draftReport

    fun createReportDraft() {
        val report = Report()

        val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
        report.toolTypeCode = ToolTypeCode.valueOf(prefs.getString("default_tool_type", ToolTypeCode.NETS.code))
        draftReport.value = report
    }

    fun setSelectedToolCode(code: ToolTypeCode) {
        if ((selectedTool.value?.toolTypeCode != code) && (code != null)) {
            selectedTool.value?.toolTypeCode != code
            selectedToolTypeCodeName.value = code.getLocalizedName(getApplication())
        }
    }


    val selectedToolCodeName: LiveData<String>
        get() = this.selectedToolTypeCodeName


    fun setSelectedToolDate(date : Date) {
        if ((date != null) && (selectedTool.value != null)) {
            // TODO: pick out only date part (not time)
            selectedTool.value!!.lastChangedDateTime = date
        }
    }


    fun sendRetrievedReport(tool: FishingFacility, comment : String?) {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val contactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        val contactPersonName = prefs.getString(context.getString(R.string.pref_contact_person_name), "")
        val contactPersonPhone = prefs.getString(context.getString(R.string.pref_contact_person_phone), "")
        val retrievalDate = Date()
        val info = RetrievalInfoDto(contactPersonEmail!!, contactPersonName!!, contactPersonPhone!!, retrievalDate, comment)

        FishingFacilityRepository.getInstance(getApplication()).sendRetrieved(tool.toolId, info)
    }


/*
    fun canSendReport():Boolean {
        if (draftReport.value == null)
            return false

        // TODO: Check that all required fields are present
        return true
    }

    fun sendReport() {
        if (canSendReport()) {

        }
    }
*/

    /*
    fun getSelectedToolCodeName():String { this.selectedToolCodeName
        var retVal : String? = null
        selectedTool?.value?.let {
            retVal = it.toolTypeCode?.getLocalizedName(getApplication())
        }
        return if (retVal != null) retVal!! else ""
    }
*/

}
