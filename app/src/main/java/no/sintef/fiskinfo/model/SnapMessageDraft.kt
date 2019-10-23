package no.sintef.fiskinfo.model

data class SnapMessageDraft(var snapMetadataID : Long) {
    var senderEmail : String = ""
    var receiverEmails : String = ""
    var message : String = ""
}
