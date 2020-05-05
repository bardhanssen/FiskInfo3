package no.sintef.fiskinfo.model.fishingfacility

import com.google.gson.annotations.SerializedName

enum class ResponseStatus(val status : String) {
    @SerializedName("NoResponse")
    NO_RESPONSE("NoResponse"),

    @SerializedName("ResponseUnknown")
    RESPONSE_UNKNOWN("ResponseUnknown"),

    @SerializedName("ResponseApproved")
    RESPONSE_APPROVED("ResponseApproved"),

    @SerializedName("ResponseRejected")
    RESPONSE_REJECTED("ResponseRejected")
}
