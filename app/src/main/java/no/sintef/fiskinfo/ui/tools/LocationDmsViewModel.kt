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
package no.sintef.fiskinfo.ui.tools

import android.app.Application
import android.location.Location
import android.location.Location.*
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.util.DMSLocation


class LocationDmsViewModel(application: Application): LocationViewModel(application) {
    var format = FORMAT_SECONDS
    var latitudeSouth : Boolean = false
    var longitudeWest: Boolean = false

    private var _latitudeDegrees = MutableStateFlow("1")
    private var _latitudeMinutes = MutableStateFlow("2")
    private var _latitudeSeconds = MutableStateFlow("0")

    private var _longitudeDegrees = MutableStateFlow("3")
    private var _longitudeMinutes = MutableStateFlow("4")
    private var _longitudeSeconds = MutableStateFlow("0")

    var latitudeDegrees: MutableStateFlow<String> = _latitudeDegrees
    var latitudeMinutes: MutableStateFlow<String> = _latitudeMinutes
    var latitudeSeconds: MutableStateFlow<String> = _latitudeSeconds

    var longitudeDegrees: MutableStateFlow<String> = _longitudeDegrees
    var longitudeMinutes: MutableStateFlow<String> = _longitudeMinutes
    var longitudeSeconds: MutableStateFlow<String> = _longitudeSeconds

    override fun setNewLocation(location: Location) {
        viewModelScope.launch {
            val dmsLocation = DMSLocation.fromLocation(location)

            _latitudeDegrees.value = dmsLocation.latitudeDegrees.toString()
            _latitudeMinutes.value = dmsLocation.latitudeMinutes.toString()
            _latitudeSeconds.value = dmsLocation.latitudeSeconds.toString()

            _longitudeDegrees.value = dmsLocation.longitudeDegrees.toString()
            _longitudeMinutes.value = dmsLocation.longitudeMinutes.toString()
            _longitudeSeconds.value = dmsLocation.longitudeSeconds.toString()

            Log.e("setNewLocation", "Updated view model location: ${location.latitude}, ${location.longitude}")
        }
    }

    override fun getLocation(): Location {
        val location = Location("")
        Log.e("getLocation", "Updated view model location: lat(${latitudeDegrees.value}, ${latitudeMinutes.value}, ${latitudeSeconds.value}), lon(${longitudeDegrees.value}, ${longitudeMinutes.value}, ${longitudeSeconds.value})")

        location.latitude = latitudeDegrees.value.toInt() + (latitudeMinutes.value.toInt() / 60.0) + (latitudeSeconds.value.toDouble() / 3600.0)
        if (latitudeSouth) location.latitude = -location.latitude

        location.longitude = longitudeDegrees.value.toInt() + (longitudeMinutes.value.toInt() / 60.0) + (longitudeSeconds.value.toDouble() / 3600.0)
        if (longitudeWest) location.longitude = -location.longitude

        Log.e("getLocation", "Loc: ${location.latitude}, ${location.longitude}")

        return location
    }
}