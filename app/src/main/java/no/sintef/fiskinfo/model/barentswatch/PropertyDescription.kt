package no.sintef.fiskinfo.model.barentswatch

data class PropertyDescription (
    var Id : Int = 0,
    var ServiceTypeId: Int = 0,
    var Name: String? = null,
    var LayerName: String? = null,
    var ApiName: String? = null,
    var UpdateFrequencyText: String? = null,
    var Description: String? = null,
    var LongDescription: String? = null,
    var DataOwner: String? = null,
    var DataOwnerLink: String? = null,
    var Status: Boolean = false,
    var Formats: Array<String>? = null,
    var SubscriptionInterval: Array<String>? = null,
    var Created: String? = null,
    var LastUpdated: String? = null,
    var Role: String? = null,
    var ErrorType: String? = null,
    var ErrorText: String? = null
)