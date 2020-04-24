package no.sintef.fiskinfo.model.fishingfacility

/*
 FishingFacility:
      required:
        - ToolId
      properties:
        ToolId:
          $ref: '#/components/schemas/ToolId'
        VesselName:
          $ref: '#/components/schemas/VesselName'
        IRCS:
          $ref: '#/components/schemas/IRCS'
        MMSI:
          $ref: '#/components/schemas/MMSI'
        IMO:
          $ref: '#/components/schemas/IMO'
        RegNum:
          $ref: '#/components/schemas/RegNum'
        VesselPhone:
           $ref: '#/components/schemas/VesselPhone'
        VesselEmail:
          $ref: '#/components/schemas/VesselEmail'
        ToolTypeCode:
          $ref: '#/components/schemas/ToolTypeCode'
        ToolTypeName:
           $ref: '#/components/schemas/ToolTypeName'
        ToolColor:
           $ref: '#/components/schemas/ToolColor'
        SetupDateTime:
           $ref: '#/components/schemas/SetupDateTime'
        RemovedDateTime:
           $ref: '#/components/schemas/RemovedDateTime'
        Source:
           $ref: '#/components/schemas/Source'
        LastChangedDateTime:
           $ref: '#/components/schemas/LastChangedDateTime'
        Comment:
           $ref: '#/components/schemas/Comment'
        Geometry:
           $ref: '#/components/schemas/Geometry'
        ShortComment:
           $ref: '#/components/schemas/ShortComment'
        LastChangedBySource:
           $ref: '#/components/schemas/LastChangedBySource'

 */



data class FishingFacility (
    var ToolId : String,

    var VesselName: String?,
    var IRCS: String?,
    var MMSI: Int?,
    var IMO: Int?,
    var RegNum: String?,
    var VesselPhone: String?,
    var VesselEmail: String?,
    var ToolTypeCode: String?, // enum: [NETS, LONGLINE, CRABPOT, DANPURSEINE]
    var ToolTypeName: String?,
    var ToolColor: String?,
    var SetupDateTime: String?, //      format: date-time
    var RemovedDateTime: String?, //      format: date-time
    var Source: String?,
    var LastChangedDateTime: String?, //     format: date-time
    var Comment: String?,
    var Geometry: Any?, // TODO: Object
    var ShortComment: String?,
    var LastChangedBySource: String? //     format: date-time
)