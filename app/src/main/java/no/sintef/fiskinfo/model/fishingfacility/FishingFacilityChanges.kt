package no.sintef.fiskinfo.model.fishingfacility

/*
    FishingFacilityChanges:
      description: Arrays of confirmed tools, unconfirmed tools, pending reports and declined reports
      properties:
        confirmed_tools:
          type: array
          items:
            $ref: '#/components/schemas/FishingFacility'
          description: List of fishingfacilities which have been confirmed, except tools which have a pending retrieval report.
        unconfirmed_tools:
          type: array
          items:
            $ref: '#/components/schemas/FishingFacility'
          description: List of fishingfacilities which are pending approval
        pending_reports:
          type: array
          items:
            $ref: '#/components/schemas/Report'
          description: List of reports pending approval (both deployed and retrieved)
        declined_reports:
          type: array
          items:
            $ref: '#/components/schemas/Report'
          description: List of rejected reports (both deployed and retrieved)
        failed_reports:
          type: array
          items:
            $ref: '#/components/schemas/Report'
          description: List of reports that cannot be sent to KVS because of a technical or validation errors
 */


data class FishingFacilityChanges (
    var confirmedTools : List<FishingFacility>,
    var unconfirmedTools : List<FishingFacility>,
    var pendingReports : List<Report>,
    var declinedReports : List<Report>,
    var failedReports : List<Report>)
