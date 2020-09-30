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

data class FiskInfoProfile ( var userId : String, var ircs : String, var mmsi: String? = null, var vesselName: String )