package no.sintef.fiskinfo.model.fishingfacility

import java.util.*

/*

DeploymentInfo:
      type: object
      description: Information about fishing gear that has been deployed
      required:
        - SetupTime
        - IRCS
        - GeometryWKT
        - ToolTypeCode
        - ContactPersonName
        - ContactPersonPhone
        - ContactPersonEmail
      properties:
        ircs:
          $ref: '#/components/schemas/IRCS'
        mmsi:
          $ref: '#/components/schemas/MMSI'
        imo:
          $ref: '#/components/schemas/IMO'
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
        contactPersonName:
          $ref: '#/components/schemas/ContactPersonName'
        contactPersonPhone:
          $ref: '#/components/schemas/ContactPersonPhone'
        contactPersonEmail:
          $ref: '#/components/schemas/ContactPersonEmail'
        comment:
          $ref: '#/components/schemas/ReportComment'
        setupTime:
           $ref: '#/components/schemas/SetupDateTime'
        currentTime:
          $ref: '#/components/schemas/CurrentTime'



 */

data class DeploymentInfo (
    var ircs: String,
    var mmsi: Int? = null,
    var imo: Int? = null,
    var regNum: String? = null,

    var vesselName: String? = null,
    var vesselPhone: String? = null,

    var toolTypeCode: ToolTypeCode = ToolTypeCode.NETS,

    var geometryWKT: String, // TODO:  Geometry in WKT (WellKnownText) format. Coordinates in latlong (epsg:4326). Points and LineStrings are valid. Example linestring with two points LINESTRING(5.592542 62.573817,5.593198 62.574123) example: POINT(5.7348 62.320717)

    var contactPersonName: String ,
    var contactPersonPhone: String,
    var contactPersonEmail: String, // format: email

    var comment: String?  = null,
    var setupTime: Date,
    var currentTime: Date?  = null
)
