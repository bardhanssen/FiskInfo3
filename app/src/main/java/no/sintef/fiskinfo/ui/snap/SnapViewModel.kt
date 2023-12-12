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
package no.sintef.fiskinfo.ui.snap

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.SnapMetadata
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.model.SnapMessageDraft
import no.sintef.fiskinfo.repository.SnapRepository

class SnapViewModel(application: Application) : AndroidViewModel(application) {

    private val selectedSnap = MutableLiveData<SnapMessage?>()
    private var selectedIsIncomming = MutableLiveData<Boolean>()
    private var inboxSnaps: LiveData<List<SnapMessage>>? = null
    private var outboxSnaps: LiveData<List<SnapMessage>>? = null
    private val draftMessage = MutableLiveData<SnapMessageDraft?>()

    val sharePublicly = MutableLiveData<Boolean>()
    val draftMetadata = MutableLiveData<SnapMetadata>()

    val draftSnapReceivers = ObservableField<String>()

    val draft: LiveData<SnapMessageDraft?>
        get() = draftMessage

    fun isIncomming(): LiveData<Boolean> {
        return selectedIsIncomming
    }

    fun selectSnap(snap: SnapMessage?, incomming: Boolean) {
        selectedIsIncomming.value = incomming
        selectedSnap.value = snap
    }

    fun deleteSelectedSnap() {
        if (selectedSnap.value != null) {
            if (selectedIsIncomming.value!!)
                SnapRepository.getInstance(getApplication()).deleteInboxSnap(selectedSnap.value!!)
            else
                SnapRepository.getInstance(getApplication()).deleteOutboxSnap(selectedSnap.value!!)
        }
        selectedIsIncomming.value = true
        selectedSnap.value = null
    }


    fun getSelectedSnap(): LiveData<SnapMessage?> {
        return selectedSnap
    }

    fun createDraftFrom(snapMetadata: SnapMetadata) {
        val snap = SnapMessageDraft(snapMetadata.id)

        sharePublicly.value = true
        draftMessage.value = snap
        draftMetadata.value = snapMetadata
        draftSnapReceivers.set("")
    }

    fun sendSnapAndClear() {
        val draft = draftMessage.value ?: return

        if (draftSnapReceivers.get() != null) {
            // TODO: Add validation
            draft.receiverEmails = draftSnapReceivers.get()!!
            /*
            val receiverList =
                Arrays.asList(*draftSnapReceivers.get()!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            draft!!.receivers = ArrayList()
            for (receiver in receiverList) {
                if (!receiver.trim().isEmpty())
                    draft.receivers!!.add(SnapReceiver(receiver.trim() ))
            }*/
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(getApplication())
        draft.senderEmail =
            prefs.getString(getApplication<Application>().getString(R.string.pref_user_identity), "ola@fiskinfo.no")!!

        SnapRepository.getInstance(getApplication()).sendSnap(draft)
        draftMessage.value = null
        draftSnapReceivers.set("")
    }

    fun getInboxSnaps(): LiveData<List<SnapMessage>>? {
        if (inboxSnaps == null) {
            inboxSnaps = SnapRepository.getInstance(getApplication()).inboxSnaps
        }
        return inboxSnaps
    }

    fun getOutboxSnaps(): LiveData<List<SnapMessage>>? {
        if (outboxSnaps == null) {
            outboxSnaps = SnapRepository.getInstance(getApplication()).outboxSnaps
        }
        return outboxSnaps
    }

    fun refreshInboxContent() {
        SnapRepository.getInstance(getApplication()).refreshInboxContent()
    }

    fun refreshOutboxContent() {
        SnapRepository.getInstance(getApplication()).refreshOutboxContent()
    }
}
