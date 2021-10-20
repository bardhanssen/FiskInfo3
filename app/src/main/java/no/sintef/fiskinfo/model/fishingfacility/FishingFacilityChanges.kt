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
    var pendingChangeReports: List<FishingFacility>,
    var failedChangeReports: List<FishingFacility>)
