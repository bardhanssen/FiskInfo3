package no.sintef.fiskinfo.dal.sprice

import javax.inject.Inject

class SpriceRepository @Inject constructor(
private val imageUriEntryDAO: ImageUriEntryDAO,
private val icingReportDAO: IcingReportDAO
){
    suspend fun getAll() = imageUriEntryDAO.getAll()
    suspend fun getAllWithId(id: String) = imageUriEntryDAO.getAllWithId(id)
    suspend fun insertMultiple(imageUriEntry: List<ImageUriEntry>) = imageUriEntryDAO.insertAll(imageUriEntry)
    suspend fun getRecordCount(imageUriEntry: ImageUriEntry) = imageUriEntryDAO.delete(imageUriEntry)

}