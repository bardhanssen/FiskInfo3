package no.sintef.fiskinfo.model.sprice

import android.content.Context
import com.google.gson.annotations.SerializedName
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.ui.sprice.IDropDownMenu

enum class ReasonForIcingOnVesselOrPlatformEnum(val code : String, val stringResource : Int) : IDropDownMenu {
    @SerializedName("NOT_SELECTED")
    NOT_SELECTED("NOT_SELECTED", R.string.enum_not_selected),
    @SerializedName("ICING_FROM_SEA_SPRAY")
    ICING_FROM_SEA_SPRAY("ICING_FROM_SEA_SPRAY", R.string.reason_for_icing_on_vessel_or_platform_1),
    @SerializedName("ICING_FROM_FOG")
    ICING_FROM_FOG("ICING_FROM_FOG", R.string.reason_for_icing_on_vessel_or_platform_2),
    @SerializedName("ICING_FROM_SEA_SPRAY_AND_FOG")
    ICING_FROM_SEA_SPRAY_AND_FOG("ICING_FROM_SEA_SPRAY_AND_FOG", R.string.reason_for_icing_on_vessel_or_platform_3),
    @SerializedName("ICING_FROM_RAIN")
    ICING_FROM_RAIN("ICING_FROM_RAIN", R.string.reason_for_icing_on_vessel_or_platform_4),
    @SerializedName("ICING_FROM_SEA_SPRAY_AND_RAIN")
    ONLY_NEW_ICE("ICING_FROM_SEA_SPRAY_AND_RAIN", R.string.reason_for_icing_on_vessel_or_platform_5),
    ;

    override fun getLocalizedName(context: Context): String {
        return context.resources.getString(stringResource)
    }

    override fun getFormValue(): String {
        val retval: String = when (this) {
            ICING_FROM_SEA_SPRAY -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_1
            ICING_FROM_FOG -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_2
            ICING_FROM_SEA_SPRAY_AND_FOG -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_3
            ICING_FROM_RAIN -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_4
            ONLY_NEW_ICE -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_5
            NOT_SELECTED -> REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_NOT_SELECTED
        }

        return retval
    }

    companion object {
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_NOT_SELECTED = "Ikke valgt"
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_1 = "1- Ising fra sjøsprøyt "
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_2 = "2- Ising fra tåke "
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_3 = "3- Ising fra sjøspryt og tåke "
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_4 = "4- Ising fra regn "
        const val REASON_FOR_ICING_ON_VESSEL_OR_PLATFORM_5 = "5- Ising fra sjøsprøyt og regn"
    }
}