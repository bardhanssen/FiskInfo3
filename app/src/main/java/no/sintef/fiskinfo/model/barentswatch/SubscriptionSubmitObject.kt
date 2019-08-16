package no.sintef.fiskinfo.model.barentswatch

data class SubscriptionSubmitObject (
    var GeoDataServiceName: String? = null,
    var FileFormatType: String? = null,
    var UserEmail: String? = null,
    var SubscriptionEmail: String? = null,
    var SubscriptionIntervalName: String? = null
)