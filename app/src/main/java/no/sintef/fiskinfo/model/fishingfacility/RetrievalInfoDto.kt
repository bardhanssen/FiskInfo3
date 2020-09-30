package no.sintef.fiskinfo.model.fishingfacility

import java.util.*

/**
"BW.Api.Api.FishingFacilityChanges.Models.RetrievalInfoDto": {
    "required": [
        "contactPersonEmail",
        "contactPersonName",
        "contactPersonPhone",
        "removedDateTime"
    ],
    "type": "object",
    "properties": {
        "removedDateTime": {
            "type": "string",
            "description": "Timestamp when the fishingfacility / tool was removed",
            "format": "date-time"
        },
        "contactPersonName": {
            "type": "string",
            "description": "Name of contact person for this change",
            "example": "Ola Nordmann"
        },
        "contactPersonPhone": {
            "type": "string",
            "example": "20990099"
        },
        "contactPersonEmail": {
            "type": "string",
            "example": "ola.nordmann@example.com"
        },
        "comment": {
            "type": "string",
            "description": "Optional comment for KVS",
            "nullable": true,
            "example": ""
        },
        "currentTime": {
            "type": "string",
            "format": "date-time",
            "nullable": true
        }
    },
    "additionalProperties": false,
    "description": "Information about fishing gear that has been retrieved"
},
 *
 */


data class RetrievalInfoDto(
    val contactPersonEmail : String,
    val contactPersonName : String,
    val contactPersonPhone : String,
    val removedDateTime : Date,
    val comment : String? = null,
    val currentTime : Date? = null)