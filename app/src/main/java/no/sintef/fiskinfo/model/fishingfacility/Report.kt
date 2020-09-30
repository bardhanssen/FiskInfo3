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

/*
 *
    Report:
      properties:
        id:
          type: string
          format: uuid
        toolId:
          $ref: '#/components/schemas/ToolId'
        imo:
          $ref: '#/components/schemas/IMO'
        ircs:
          $ref: '#/components/schemas/IRCS'
        mmsi:
          $ref: '#/components/schemas/MMSI'
        regNum:
          $ref: '#/components/schemas/RegNum'
        vesselName:
          $ref: '#/components/schemas/VesselName'
        vesselPhone:
          $ref: '#/components/schemas/VesselPhone'
        toolTypeCode:
          $ref: '#/components/schemas/ToolTypeCode'
        geometryWKT:
          $ref: '#/components/schemas/GeometryWKT'
        type:
          $ref: '#/components/schemas/FishingFacilityChangeType'
        confirmed:
          type: boolean
        changedDateTime:
          $ref: '#/components/schemas/ChangedDateTime'
        contactPersonname:
          $ref: '#/components/schemas/ContactPersonName'
        contactPersonPhone:
          $ref: '#/components/schemas/VesselPhone'
        contactPersonEmail:
          $ref: '#/components/schemas/VesselEmail'
        comment:
          $ref: '#/components/schemas/Comment'
        currentTime:
          $ref: '#/components/schemas/CurrentTime'
        deletedByUser:
          $ref: '#/components/schemas/DeletedByUser'
        responseStatus:
          $ref: '#/components/schemas/ResponseStatus'
        responseReason:
          $ref: '#/components/schemas/ResponseReason'
        responseDateTime:
          $ref: '#/components/schemas/ResponseDateTime'
        errorReportedFromApi:
          $ref: '#/components/schemas/ErrorReportedFromApi'
 */


data class Report (
    var id : String? = null,
    var toolId : String? = null,
    var userId : String? = null, //TODO: Check if this is officially included

    var imo: Int? = null,
    var ircs: String? = null,
    var mmsi: Int? = null,

    var regNum: String? = null,
    var vesselName: String? = null,
    var vesselPhone: String? = null,
    var toolTypeCode: ToolTypeCode? = ToolTypeCode.NETS,

    var geometryWKT: String? = null, // TODO:  Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)

    var type: FishingFacilityChangeType? = FishingFacilityChangeType.DEPLOYED,
    var confirmed: Boolean? = false,

    var changedDateTime: Date? = Calendar.getInstance().time,

    var contactPersonName: String? = null,
    var contactPersonPhone: String? = null,
    var contactPersonEmail: String?  = null, // format: email
    var comment: String?  = null,
    var currentTime: Date?  = null,
    var deletedByUser: Boolean? = false,
    var responseStatus: ResponseStatus? = ResponseStatus.NO_RESPONSE,
    var responseReason: String?  = null,
    var responseDateTime: Date?  = null,
    var errorReportedFromApi: Boolean? = null
)

/*
      "id": 2887,
      "toolId": "AC2FE38D-4815-465B-8B7F-C9CC4C0D2508",
      "userId": "tore.syversen@sintef.no",
      "imo": "9230036",
      "ircs": "LLOP",
      "mmsi": "258410000",
      "regNum": "F-7-L",
      "vesselName": "HERMES",
      "vesselPhone": null,
      "toolTypeCode": "LONGLINE",
      "geometryWKT": "POINT(14.512939 68.833661)",
      "type": 3,
      "confirmed": false,
      "changedDateTime": "2020-03-26T08:30:13.65",
      "contactPersonName": "Tore",
      "contactPersonPhone": "90913338",
      "contactPersonEmail": "tore.syversen@sintef.no",
      "comment": " ",
      "currentTime": "2020-03-26T08:30:34.784",
      "currentPositionLat": null, //TODO: Include?
      "currentPositionLon": null, //TODO: Include?
      "deletedByUser": false,
      "responseStatus": "NoResponse",
      "responseReason": null,
      "responseDateTime": null,
      "errorReportedFromApi": false


 */