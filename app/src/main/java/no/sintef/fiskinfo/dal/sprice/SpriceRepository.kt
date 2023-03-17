package no.sintef.fiskinfo.dal.sprice

import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload
import javax.inject.Inject

class SpriceRepository @Inject constructor(
private val imageUriEntryDAO: ImageUriEntryDAO,
private val icingReportDAO: IcingReportDAO
){
    // Images
    suspend fun getAllImageUris() = imageUriEntryDAO.getAll()
    suspend fun getAllImageUrisWithId(id: String) = imageUriEntryDAO.getAllWithId(id)
    suspend fun insertMultipleImageUris(imageUriEntry: List<ImageUriEntry>) = imageUriEntryDAO.insertAll(imageUriEntry)
    suspend fun getNumberOfImageUris(imageUriEntry: ImageUriEntry) = imageUriEntryDAO.delete(imageUriEntry)

    // Reports
    suspend fun getIcingReports() = icingReportDAO.getAll()
    suspend fun getIcingReportWithId(id: String) = icingReportDAO.getWithId(id)
    suspend fun getUnreportedIcingReports() = icingReportDAO.getUnreportedReports()
    suspend fun deleteIcingReport(reportIcingRequestBody: ReportIcingRequestPayload) = icingReportDAO.delete(reportIcingRequestBody)
    suspend fun insertIcingReport(reportIcingRequestBody: ReportIcingRequestPayload) = icingReportDAO.insert(reportIcingRequestBody)
    suspend fun insertMultiple(reportIcingRequestBodyList: List<ReportIcingRequestPayload>) = icingReportDAO.insertMultiple(reportIcingRequestBodyList)
}