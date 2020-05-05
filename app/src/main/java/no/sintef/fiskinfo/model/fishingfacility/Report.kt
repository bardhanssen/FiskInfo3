package no.sintef.fiskinfo.model.fishingfacility

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
    var id : String?,
    var toolId : String?,
    var userId : String?, //TODO: Check if this is officially included

    var imo: Int?,
    var ircs: String?,
    var mmsi: Int?,

    var regNum: String?,
    var vesselName: String?,
    var vesselPhone: String?,
    var toolTypeCode: String?, // TODO: enum: [NETS, LONGLINE, CRABPOT, DANPURSEINE]

    var geometryWKT: String?, // TODO:  Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)

    var type: String?, // FishingFacilityChangeType enum: [Retrieved, Deployed]
    var confirmed: Boolean?,

    var changedDateTime: String?, // TODO:    format: date-time

    var contactPersonName: String?,
    var contactPersonPhone: String?,
    var contactPersonEmail: String?, // format: email
    var comment: String?,
    var currentTime: String?,  // TODO:    format: date-time
    var deletedByUser: Boolean?,
    var responseStatus: String?, // TODO: enum :       enum: 'NoResponse', 'ResponseUnknown', 'ResponseApproved', 'ResponseRejected'
    var responseReason: String?,
    var responseDateTime: String?, // TODO: format: date-time
    var errorReportedFromApi: Boolean
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