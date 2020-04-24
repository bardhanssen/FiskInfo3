package no.sintef.fiskinfo.model.fishingfacility

/*
 *
 *  Report:
      properties:
        Id:
          type: string
          format: uuid
        ToolId:
          $ref: '#/components/schemas/ToolId'
        Imo:
          $ref: '#/components/schemas/IMO'
        Mmsi:
          $ref: '#/components/schemas/MMSI'
        RegNum:
          $ref: '#/components/schemas/RegNum'
        VesselName:
          $ref: '#/components/schemas/VesselName'
        VesselPhone:
          $ref: '#/components/schemas/VesselPhone'
        ToolTypeCode:
          $ref: '#/components/schemas/ToolTypeCode'
        GeometryWKT:
          $ref: '#/components/schemas/GeometryWKT'
        Type:
          $ref: '#/components/schemas/FishingFacilityChangeType'
        Confirmed:
          type: boolean
        ChangedDateTime:
          $ref: '#/components/schemas/ChangedDateTime'
        ContactPersonname:
          $ref: '#/components/schemas/ContactPersonName'
        ContactPersonPhone:
          $ref: '#/components/schemas/VesselPhone'
        ContactPersonEmail:
          $ref: '#/components/schemas/VesselEmail'
        Comment:
          $ref: '#/components/schemas/Comment'
        CurrentTime:
          $ref: '#/components/schemas/CurrentTime'
 *
 */


data class Report (
    var Id : String?,
    var ToolId : String?,

    var Imo: Int?,
    var Mmsi: Int?,

    var RegNum: String?,
    var VesselName: String?,
    var VesselPhone: String?,
    var ToolTypeCode: String?, // enum: [NETS, LONGLINE, CRABPOT, DANPURSEINE]

    var GeometryWKT: String?, // Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)

    var Type: String?, // FishingFacilityChangeType enum: [Retrieved, Deployed]
    var Confirmed: Boolean?,

    var ChangedDateTime: String?, //     format: date-time

    var ContactPersonname: String?,
    var ContactPersonPhone: String?,
    var ContactPersonEmail: String?, // format: email
    var Comment: String?,
    var CurrentTime: String?  //     format: date-time
    )