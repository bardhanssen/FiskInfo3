package no.sintef.fiskinfo.model.sprice.enums

import android.content.Context
import com.google.gson.annotations.SerializedName
import no.sintef.fiskinfo.R

enum class SpriceReportListTypeEnum(val type: String, val stringResource : Int) {
    @SerializedName("LAST_24_HOURS")
    LAST_24_HOURS("LAST_24_HOURS", R.string.sprice_report_list_type_last_24_hours),

    @SerializedName("ALL")
    ALL("ALL", R.string.sprice_report_list_type_all);

    fun getLocalizedName(context : Context):String {
        return context.resources.getString(stringResource)
    }
}

