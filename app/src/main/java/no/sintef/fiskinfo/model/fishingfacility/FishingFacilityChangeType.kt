package no.sintef.fiskinfo.model.fishingfacility

import com.google.gson.annotations.SerializedName

enum class FishingFacilityChangeType(val changeType : Int) {
    @SerializedName("1")
    RETRIEVED(1),

    @SerializedName("3")
    DEPLOYED(3)
}
