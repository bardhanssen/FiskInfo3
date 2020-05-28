package no.sintef.fiskinfo.ui.tools

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.model.fishingfacility.FiskInfoProfileDTO
import no.sintef.fiskinfo.model.fishingfacility.Report
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import java.util.*

class ToolsViewModel(application: Application) : AndroidViewModel(application)  {

    private val selectedTool = MutableLiveData<FishingFacility?>()
    private var confirmedTools: LiveData<List<FishingFacility>>? = null
    private var unconfirmedTools: LiveData<List<FishingFacility>>? = null

    private var profile: LiveData<FiskInfoProfileDTO>? = null

    private val draftReport = MutableLiveData<Report>()

    fun selectTool(tool: FishingFacility?, isConfirmed: Boolean) {
        //selectedIsIncomming.value = isConfirmed
        selectedTool.value = tool
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



}
