package no.sintef.fiskinfo.model.sprice.enums

import com.google.gson.annotations.SerializedName

enum class CardinalDirections(val direction: String) {
    @SerializedName("W")
    W("W"),
    @SerializedName("E")
    E("E"),
    @SerializedName("S")
    S("S"),
    @SerializedName("N")
    N("N")
}