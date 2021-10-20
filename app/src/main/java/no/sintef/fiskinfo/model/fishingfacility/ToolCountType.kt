package no.sintef.fiskinfo.model.fishingfacility

import com.google.gson.annotations.SerializedName
import no.sintef.fiskinfo.R

enum class ToolCountType (val code : String, val stringResource : Int) {
    @SerializedName("Length")
    LENGTH("Length", R.string.tool_tool_count_length),

    @SerializedName("Count")
    COUNT("Count", R.string.tool_tool_count)

}