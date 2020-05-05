package no.sintef.fiskinfo.model.fishingfacility

/*
    FishingFacility:
      required:
        - ToolId
      properties:
        toolId:
          $ref: '#/components/schemas/ToolId'
        vesselName:
          $ref: '#/components/schemas/VesselName'
        ircs:
          $ref: '#/components/schemas/IRCS'
        mmsi:
          $ref: '#/components/schemas/MMSI'
        imo:
          $ref: '#/components/schemas/IMO'
        regNum:
          $ref: '#/components/schemas/RegNum'
        vesselPhone:
           $ref: '#/components/schemas/VesselPhone'
        vesselEmail:
          $ref: '#/components/schemas/VesselEmail'
        toolTypeCode:
          $ref: '#/components/schemas/ToolTypeCode'
        toolTypeName:
           $ref: '#/components/schemas/ToolTypeName'
        toolColor:
           $ref: '#/components/schemas/ToolColor'
        setupDateTime:
           $ref: '#/components/schemas/SetupDateTime'
        removedDateTime:
           $ref: '#/components/schemas/RemovedDateTime'
        source:
           $ref: '#/components/schemas/Source'
        lastChangedDateTime:
           $ref: '#/components/schemas/LastChangedDateTime'
        comment:
           $ref: '#/components/schemas/Comment'
        geometry:
           $ref: '#/components/schemas/Geometry'
        shortComment:
           $ref: '#/components/schemas/ShortComment'
        lastChangedBySource:
           $ref: '#/components/schemas/LastChangedBySource'
 */

data class FishingFacility (
    var toolId : String,

    var vesselName: String?,
    var ircs: String?,
    var mmsi: Int?,
    var imo: Int?,
    var regNum: String?,
    var vesselPhone: String?,
    var vesselEmail: String?,
    var toolTypeCode: String?, // TODO: enum [NETS, LONGLINE, CRABPOT, DANPURSEINE]
    var toolTypeName: String?,
    var toolColor: String?,
    var setupDateTime: String?, //      format: date-time
    var removedDateTime: String?, //      format: date-time
    var source: String?,
    var lastChangedDateTime: String?, //     format: date-time
    var comment: String?,
    //var geometry: Any?, // TODO: Object
    //var shortComment: String?,
    var lastChangedBySource: String?, //     format: date-time
    var geometryWKT: String? // TODO: Object
)

/* Example
      "toolId": "0FB332A8-ABB1-4782-9048-6299FE2D949F",
      "vesselName": "HERMES",
      "ircs": "LLOP",
      "mmsi": "258410000",
      "imo": "9230036",
      "regNum": null,
      "vesselPhone": null,
      "vesselEmail": "tore.syversen@sintef.no",
      "toolTypeCode": "DANPURSEINE",
      "toolTypeName": "Danish- / Purse- Seine",
      "toolColor": "#000000",
      "setupDateTime": "2020-03-20T14:13:35.851+01:00",
      "removedDateTime": null,
      "source": "BW-QA",
      "lastChangedDateTime": "2020-03-27T12:52:41.287815+01:00",
      "comment": "",
      "lastChangedBySource": null,
      "geometryWKT": "POINT(14.512939 68.833661)"
 */


