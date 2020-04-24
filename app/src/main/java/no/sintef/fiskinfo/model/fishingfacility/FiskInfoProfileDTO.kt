package no.sintef.fiskinfo.model.fishingfacility

/*
    FiskInfoProfileDto:
      type: object
      description: Profile information for a user. Information about rights and if the
        user is connected to a vessel.
      required:
        - HaveProfile
        - HaveDownloadRights
      properties:
        HaveProfile:
          type: boolean
          description: Wether the user has a FiskInfo profile.
          example: true
        HaveDownloadRights:
          type: boolean
          description: Wether the user has download rights for the full FishingFacility-dataset
        FiskinfoProfile:
          type: object
          required:
            - UserId
            - Ircs
            - VesselName
          properties:
            UserId:
              type: string
              description: Username
            Ircs:
              $ref: '#/components/schemas/IRCS'
            Mmsi:
              $ref: '#/components/schemas/MMSI'
            VesselName:
              $ref: '#/components/schemas/VesselName'
 */


data class FiskInfoProfileDTO (var haveProfile: Boolean, var haveDownloadRights: Boolean, var fiskinfoProfile: FiskInfoProfile? = null)
