package no.sintef.fiskinfo.model.fishingfacility

import android.location.Location
import no.sintef.fiskinfo.util.wktToLocations
import java.util.*

data class ToolViewModel(
    // Fields from Report or FishingFacilities
    var toolId : String? = null,
    var reportId : String? = null,
    var toolTypeCode: ToolTypeCode? = ToolTypeCode.NETS,
    var geometryWKT: String? = null, // TODO:  Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)
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
    )
  {
    fun getLocations():List<Location> {
        return wktToLocations(geometryWKT)
  }

}