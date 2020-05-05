package no.sintef.fiskinfo.model.fishingfacility

import com.google.gson.annotations.SerializedName

// TODO: enum [NETS, LONGLINE, CRABPOT, DANPURSEINE]

enum class ToolTypeCode(val code : String) {
    @SerializedName("NETS")
    NETS("NETS"),

    @SerializedName("LONGLINE")
    LONGLINE("LONGLINE"),

    @SerializedName("CRABPOT")
    CRABPOT("CRABPOT"),

    @SerializedName("DANPURSEINE")
    DANPURSEINE("DANPURSEINE")
}