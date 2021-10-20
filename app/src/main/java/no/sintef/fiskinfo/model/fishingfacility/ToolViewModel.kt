package no.sintef.fiskinfo.model.fishingfacility

import android.location.Location
import no.sintef.fiskinfo.util.geoJsonGeometryToLocations
import no.sintef.fiskinfo.util.wktToLocations
import java.util.*

data class ToolViewModel(
    // Fields from Report or FishingFacilities
    var toolId : String? = null,
    var reportId : String? = null,
    var toolTypeCode: ToolTypeCode? = ToolTypeCode.NETS,
    var geometry: GeoJsonGeometry = GeoJsonGeometry(GeometryType.POINT.value, arrayOf(0, 0)),
    var comment: String?  = null,

    // Fields from Report
    var type: FishingFacilityChangeType? = FishingFacilityChangeType.DEPLOYED,
    var confirmed: Boolean? = false,
    var responseStatus: ResponseStatus? = ResponseStatus.NO_RESPONSE,
    var responseReason: String?  = null,
    var responseDateTime: Date?  = null,
    var errorReportedFromApi: Boolean? = null,

    // Fields from FishingFacilities
    var setupDateTime: Date? = null,
    var lastChangedDateTime: Date? = null
    ) {

    fun getLocations():List<Location> {
        return geoJsonGeometryToLocations(geometry)
    }

    companion object{
        const val DEFAULT_DAYS_BEFORE_OLD = 7
    }
}