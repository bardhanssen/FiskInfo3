/**
 * Copyright (C) 2019 SINTEF
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
package no.sintef.fiskinfo.repository.dummy

import no.sintef.fiskinfo.model.EchogramInfo

import java.util.ArrayList
import java.util.Date

object DummyEchogram {
    internal var MILLIS_IN_MIN: Long = 60000

    //    public static LiveData<List<EchogramInfo>> getDummyEchogram() {
    val dummyEchograms: List<EchogramInfo>
        get() {
            val list = ArrayList<EchogramInfo>()
            list.add(createEchogram(0))
            list.add(createEchogram(23))
            list.add(createEchogram(84))
            list.add(createEchogram((60 * 24 - 45).toLong()))
            list.add(createEchogram((60 * 24 + 33).toLong()))
            return list
        }

    fun createEchogram(minutesOld: Long): EchogramInfo {
        val echogram = EchogramInfo()
        echogram.id = Math.round(Math.random() * 100000000)
        echogram.timestamp = Date(System.currentTimeMillis() - minutesOld * MILLIS_IN_MIN)
        echogram.biomass = "400.0"
        echogram.echogramUrl = "https://www.sintef.no"
        echogram.latitude = "N63" + 0x00B0.toChar() + "24\'48\""
        echogram.longitude = "E10" + 0x00B0.toChar() + "24\'33\" "
        echogram.userID = 1
        echogram.source = "EK80"
        return echogram
    }
}
