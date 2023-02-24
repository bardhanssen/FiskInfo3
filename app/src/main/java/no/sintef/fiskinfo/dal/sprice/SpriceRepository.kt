package no.sintef.fiskinfo.dal.sprice

import javax.inject.Inject

class SpriceRepository @Inject constructor(
private val imageUriEntryDAO: ImageUriEntryDAO,
private val icingReportDAO: IcingReportDAO
){
    suspend fun getAllImageUris() = imageUriEntryDAO.getAll()
    suspend fun getAllImageUrisWithId(id: String) = imageUriEntryDAO.getAllWithId(id)
    suspend fun insertMultipleImageUris(imageUriEntry: List<ImageUriEntry>) = imageUriEntryDAO.insertAll(imageUriEntry)
    suspend fun getNumberOfImageUris(imageUriEntry: ImageUriEntry) = imageUriEntryDAO.delete(imageUriEntry)

    suspend fun getIcingReports() = icingReportDAO.getAll()
    suspend fun getIcingReportWithId(id: String) = icingReportDAO.getWithId(id)
    suspend fun getUnreportedIcingReports() = icingReportDAO.getUnreportedReports()

}