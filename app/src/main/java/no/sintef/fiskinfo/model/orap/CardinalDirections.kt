package no.sintef.fiskinfo.model.orap

import com.google.gson.annotations.SerializedName

enum class CardinalDirections(val direction: String) {
    @SerializedName("W")
    W("W"),
    @SerializedName("W")
    E("E"),
    @SerializedName("W")
    S("S"),
    @SerializedName("W")
    N("N")
}