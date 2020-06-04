package no.sintef.fiskinfo.model.fishingfacility

import android.content.Context
import com.google.gson.annotations.SerializedName
import no.sintef.fiskinfo.R

// TODO: enum [NETS, LONGLINE, CRABPOT, DANPURSEINE]

enum class ToolTypeCode(val code : String, val stringResource : Int) {
    @SerializedName("NETS")
    NETS("NETS", R.string.tool_type_code_nets),

    @SerializedName("LONGLINE")
    LONGLINE("LONGLINE", R.string.tool_type_code_longline),

    @SerializedName("CRABPOT")
    CRABPOT("CRABPOT", R.string.tool_type_code_crabpot),

    @SerializedName("DANPURSEINE")
    DANPURSEINE("DANPURSEINE", R.string.tool_type_code_danpurseine);

    public fun getLocalizedName(context : Context):String {
        return context.resources.getString(stringResource)
    }
}