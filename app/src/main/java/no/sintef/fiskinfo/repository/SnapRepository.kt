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
package no.sintef.fiskinfo.repository

import android.content.Context
import android.preference.PreferenceManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.sintef.fiskinfo.R

import java.util.ArrayList

import no.sintef.fiskinfo.api.SnapMessageService
import no.sintef.fiskinfo.model.SnapMetadata
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.repository.dummy.DummyEchogram
import no.sintef.fiskinfo.repository.dummy.DummySnap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SnapRepository(context: Context) {
    internal var snapMessageService: SnapMessageService? = null

    internal var snapFishServerUrl: String? = DEFAULT_SNAP_FISH_SERVER_URL

    internal var outboxSnaps = MutableLiveData<ArrayList<SnapMessage>>()

    internal val inboxSnaps = MutableLiveData<List<SnapMessage>>()
    internal val echogramInfos = MutableLiveData<List<SnapMetadata>>()

    init {
        updateFromPreferences(context)
    }

    fun getInboxSnaps(): LiveData<List<SnapMessage>> {
        refreshInboxContent()
        return inboxSnaps
    }


    protected fun initService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(snapFishServerUrl!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        snapMessageService = retrofit.create(SnapMessageService::class.java)

    }


    private fun initOutbox() {
        if (outboxSnaps == null)
            outboxSnaps = MutableLiveData()
        if (outboxSnaps.value == null)
            outboxSnaps!!.value = ArrayList()
    }

    fun storeSnap(newSnap: SnapMessage) {
        if ((outboxSnaps == null) || (outboxSnaps?.value == null))
            initOutbox()

        if (snapMessageService == null)
            initService()
        snapMessageService!!.sendSnapMessage(newSnap).enqueue(object : Callback<SnapMessage> {
            override fun onResponse(call: Call<SnapMessage>, response: Response<SnapMessage>) {

            }

            override fun onFailure(call: Call<SnapMessage>, t: Throwable) {

            }
        })
        outboxSnaps!!.value!!.add(newSnap)
    }

    fun getOutboxSnaps(): MutableLiveData<ArrayList<SnapMessage>> {
        if (outboxSnaps == null) {
            outboxSnaps = MutableLiveData()
            outboxSnaps!!.setValue(ArrayList())
        }
        return outboxSnaps
    }

    fun getEchogramInfos(): LiveData<List<SnapMetadata>> {
        refreshEchogramListContent()
        return echogramInfos
    }

    fun refreshInboxContent() {
        if (snapMessageService == null)
            initService()

        snapMessageService!!.getSnapMessages(true).enqueue(object : Callback<List<SnapMessage>> {
            override fun onResponse(call: Call<List<SnapMessage>>, response: Response<List<SnapMessage>>) {
                inboxSnaps.value = response.body()
            }

            override fun onFailure(call: Call<List<SnapMessage>>, t: Throwable) {
                inboxSnaps.value = DummySnap.dummyInboxSnaps.value
            }
        })
    }

    fun refreshEchogramListContent() {
        if (snapMessageService == null)
            initService()

        snapMessageService!!.getEchogramInfos().enqueue(object : Callback<List<SnapMetadata>> {
            override fun onResponse(call: Call<List<SnapMetadata>>, response: Response<List<SnapMetadata>>) {
                echogramInfos.setValue(response.body())
            }

            override fun onFailure(call: Call<List<SnapMetadata>>, t: Throwable) {
                echogramInfos.setValue(DummyEchogram.dummyEchograms)
            }
        })
    }


    fun updateFromPreferences(context: Context?) {
        if (context != null) {
            // Find preferences
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            snapFishServerUrl = prefs.getString( context.getString(R.string.snap_api_server_address), DEFAULT_SNAP_FISH_SERVER_URL)
            snapMessageService = null
        }
        refreshInboxContent()
    }

    companion object {
        var instance: SnapRepository? = null

        //    final static String SNAP_FISH_SERVER_URL = "https://10.218.86.229:44387/";
        //    final static String SNAP_FISH_SERVER_URL = "http://10.218.69.173:58196/";
        // internal val DEFAULT_SNAP_FISH_SERVER_URL = "http://10.218.86.229:5002/"
        internal val DEFAULT_SNAP_FISH_SERVER_URL = "http://129.242.16.123:37789/"
        fun getInstance(context: Context): SnapRepository {
            if (instance == null)
                instance = SnapRepository(context)
            return instance!!
        }
    }

}
