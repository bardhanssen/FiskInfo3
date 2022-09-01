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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.util.DMSLocation
import kotlin.math.abs
import kotlin.math.floor


class LocationDmsViewModel(application: Application) : LocationViewModel(application) {
    var format = FORMAT_SECONDS
    var dmsLocation = MutableLiveData<DMSLocation>()

    private var _dmsFlow = MutableStateFlow(DMSLocation())
    var dmsFlow: MutableStateFlow<DMSLocation> = _dmsFlow

    override fun setNewLocation(location : Location) {
        viewModelScope.launch {
            dmsLocation.value = DMSLocation.fromLocation(location)
            _dmsFlow.value = DMSLocation.fromLocation(location)
            _dmsFlow.value.latitudeSeconds = 1.0// = DMSLocation.fromLocation(location)
        }

        Log.e("TAG", "Updated location: ${_dmsFlow.value.latitudeDegrees}, ${_dmsFlow.value.latitudeMinutes}, ${_dmsFlow.value.latitudeSeconds}")
        Log.e("TAG", "Updated location: ${_dmsFlow.value.longitudeDegrees}, ${_dmsFlow.value.longitudeMinutes}, ${_dmsFlow.value.longitudeSeconds}")
    }

    override fun getLocation():Location {
        return dmsFlow.value.toLocation()
    }
}