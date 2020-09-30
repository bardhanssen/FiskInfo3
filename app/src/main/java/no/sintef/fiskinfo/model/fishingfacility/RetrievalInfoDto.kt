/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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