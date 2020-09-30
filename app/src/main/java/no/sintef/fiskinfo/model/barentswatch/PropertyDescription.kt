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
package no.sintef.fiskinfo.model.barentswatch

data class PropertyDescription (
    var Id : Int = 0,
    var ServiceTypeId: Int = 0,
    var Name: String? = null,
    var LayerName: String? = null,
    var ApiName: String? = null,
    var UpdateFrequencyText: String? = null,
    var Description: String? = null,
    var LongDescription: String? = null,
    var DataOwner: String? = null,
    var DataOwnerLink: String? = null,
    var Status: Boolean = false,
    var Formats: Array<String>? = null,
    var SubscriptionInterval: Array<String>? = null,
    var Created: String? = null,
    var LastUpdated: String? = null,
    var Role: String? = null,
    var ErrorType: String? = null,
    var ErrorText: String? = null
)