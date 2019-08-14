package no.sintef.fiskinfo.model.barentswatch

data class Subscription (
    var Id: Int = 0,
    var GeoDataServiceName: String? = null,
    var FileFormatType: String? = null,
    var UserEmail: String? = null,
    var SubscriptionEmail: String? = null,
    var SubscriptionIntervalName: String? = null,
    var Created: String? = null,
    var LastModified: String? = null
)