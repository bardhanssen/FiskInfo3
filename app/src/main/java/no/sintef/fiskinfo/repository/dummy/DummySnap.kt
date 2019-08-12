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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.model.SnapReceiver
import no.sintef.fiskinfo.model.SnapUser

import java.util.ArrayList
import java.util.Date

object DummySnap {

    var USERS: ArrayList<SnapUser>? = null

    //    public static String[] me() {
    //        return new String[]{"Me"};
    //    }
    var ME = "ola@fiskinfo.no"

    //"Ola", me()));
    //"Ola", me()));
    //"Peder", new String[]{"Me", "Hans"}));
    //        list.add(createSnap(60*24 + 3, "Mer enn nok å ta av", "Har du husket du å tippe?", "Per", me()));
    //        list.add(createSnap(60*24 + 97, "Ubåt?", "", "Ola", me()));
    val dummyInboxSnaps: LiveData<List<SnapMessage>>
        get() {
            val listHolder = MutableLiveData<List<SnapMessage>>()
            val list = ArrayList<SnapMessage>()
            list.add(
                createSnap(
                    0,
                    "Mer her",
                    "Det er mer en nok å ta av, men kvota er full. Kanskje du rekker frem?",
                    getUser(0),
                    ME
                )
            )
            list.add(createSnap(12, "Torsk?", "", getUser(0), ME))
            list.add(createSnap(41, "Ser her da!", "", getUser(2), ME))
            listHolder.value = list
            return listHolder
        }

    fun createSnap(minutesAgo: Long, title: String, comment: String, sender: SnapUser, receiver: String): SnapMessage {
        val snap = SnapMessage()
        snap.id = Math.round(Math.random() * 100000000)

        snap.echogramInfo = DummyEchogram.createEchogram(minutesAgo)
        snap.echogramInfoID = snap.echogramInfo!!.id
        snap.title = title
        snap.comment = comment
        snap.sender = sender
        snap.receivers = ArrayList()
        snap.receivers!!.add(SnapReceiver(receiver))
        snap.sendTimestamp = Date()
        return snap
    }

    fun getUser(id: Int): SnapUser {
        if (USERS == null) {
            USERS = ArrayList()
            USERS!!.add(SnapUser(0, "per@fiskinfo.no", "Per"))
            USERS!!.add(SnapUser(1, ME, "Ola"))
            USERS!!.add(SnapUser(2, "jens@fiskinfo.no", "Jens"))
        }
        return USERS!![id]
    }
}
