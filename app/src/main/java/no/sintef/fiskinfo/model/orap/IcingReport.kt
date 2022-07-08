package no.sintef.fiskinfo.model.orap

import no.sintef.fiskinfo.model.fishingfacility.GeoJsonGeometry
import java.util.*

data class IcingReport(

    var geometry: GeoJsonGeometry,
    var reportingTime: Date
)
