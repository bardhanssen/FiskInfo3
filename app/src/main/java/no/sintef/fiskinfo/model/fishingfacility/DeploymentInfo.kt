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
    var toolId: String, // Actual type: UUID
    var toolTypeCode: ToolTypeCode = ToolTypeCode.NETS,

    var geometry: GeoJsonGeometry,

    var contactEmail: String,
    var contactPhone: String,

    var toolCount: Int,
    var setupTime: Date
)
