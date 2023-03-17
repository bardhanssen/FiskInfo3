package no.sintef.fiskinfo.ui.sprice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload

class SpriceViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _reports = MutableStateFlow<List<ReportIcingRequestPayload>>(listOf())

    val reports = _reports

    suspend fun init() {

    }

    fun refreshReports() {
        // TODO: Check for report updates

    }
}