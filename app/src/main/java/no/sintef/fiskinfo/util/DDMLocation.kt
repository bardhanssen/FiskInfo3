/**
 * Copyright (C) 2020 SINTEF
 *
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
package no.sintef.fiskinfo.util

import android.location.Location
import kotlin.math.abs
import kotlin.math.floor

data class DDMLocation(var latitudeSouth : Boolean = false,
                       var latitudeDegrees : Int = 0,
                       var latitudeDecimalMinutes : Double = 0.0,

                       var longitudeWest : Boolean = false,
                       var longitudeDegrees : Int = 0,
                       var longitudeDecimalMinutes : Double = 0.0) {

    companion object {
       fun fromLocation(location: Location): DDMLocation {
            var ddm = DDMLocation()
            with(ddm) {
                latitudeSouth = location.latitude < 0
                var absCoord = abs(location.latitude)
                latitudeDegrees = floor(absCoord).toInt()
                absCoord -= latitudeDegrees
                latitudeDecimalMinutes = absCoord * 60.0

                longitudeWest = location.longitude < 0
                absCoord = abs(location.longitude)
                longitudeDegrees = floor(absCoord).toInt()
                absCoord -= longitudeDegrees
                absCoord *= 60.0
                longitudeDecimalMinutes = absCoord * 60.0
            }
            return ddm;
        }
    }

    fun toLocation(): Location? {
        var loc = Location("")
        loc.latitude = latitudeDegrees + (latitudeDecimalMinutes/60.0)
        if (latitudeSouth) loc.latitude = -loc.latitude

        loc.longitude = longitudeDegrees + (longitudeDecimalMinutes/60.0)
        if (longitudeWest) loc.longitude = -loc.longitude
        return loc
    }

}
