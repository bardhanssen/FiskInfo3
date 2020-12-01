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
        FiskinfoProfile:
          type: object
          required:
            - userId
            - userName
            - ircs
            - vesselName
          properties:
            userId:
              type: string
              description: Username
            ircs:
              $ref: '#/components/schemas/IRCS'
            mmsi:
              $ref: '#/components/schemas/MMSI'
            vesselName:
              $ref: '#/components/schemas/VesselName'
 */

data class FiskInfoProfile(
    var userId: String,
    var usedName: String,
    var ircs: String,
    var mmsi: Int,
    var vesselName: String
)