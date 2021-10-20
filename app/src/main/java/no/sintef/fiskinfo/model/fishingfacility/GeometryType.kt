package no.sintef.fiskinfo.model.fishingfacility

import com.google.gson.annotations.SerializedName

enum class GeometryType(val value: String) {
    @SerializedName("Point")
    POINT("Point"),
    @SerializedName("LineString")
    LINESTRING("LineString"),
}