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
package no.sintef.fiskinfo.ui.overview

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import no.sintef.fiskinfo.repository.SnapRepository
import no.sintef.fiskinfo.util.isUserProfileValid
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.model.SnapMetadata

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    val toolRepository = FishingFacilityRepository(application)
    val snapRepository = SnapRepository.getInstance(application)
    //var confirmedTools = FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools()
    //var unconfirmedTools = FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools()

//    var overviewList = MutableLiveData<List<OverviewCardItem>>()
/*    var overviewList = CombinedLiveData<List<OverviewCardItem>>(
        FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools(),
        FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools() ) { datas: List<Any?> -> refreshOverviewItems(datas) }
*/


    fun initOverviewItemsList(): MutableLiveData<List<OverviewCardItem>> {
        val overviewItemList = MediatorLiveData<List<OverviewCardItem>>()

        val confirmedTools = toolRepository.getConfirmedTools()
        val unconfirmedTools = toolRepository.getUnconfirmedTools()

        val inboxSnaps = snapRepository.getInboxSnaps()
        val echogramInfos = snapRepository.getEchogramInfos()

        overviewItemList.addSource(confirmedTools) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools, inboxSnaps, echogramInfos)
        }
        overviewItemList.addSource(unconfirmedTools) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools, inboxSnaps, echogramInfos)
        }
        overviewItemList.addSource(inboxSnaps) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools, inboxSnaps, echogramInfos)
        }
        overviewItemList.addSource(echogramInfos) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools, inboxSnaps, echogramInfos)
        }
        return overviewItemList
    }

    var overviewList = initOverviewItemsList() //MutableLiveData<List<OverviewCardItem>>()
    /*
    private fun refreshFromPreferences() {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        var irccontactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        var contactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")

    } */

    fun refreshOverviewItems(confirmed: LiveData<List<FishingFacility>>,
                             unconfirmed: LiveData<List<FishingFacility>>,
                            inboxSnaps: LiveData<List<SnapMessage>>,
                            echogramInfos : LiveData<List<SnapMetadata>>):ArrayList<OverviewCardItem>
    {
        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList, confirmed, unconfirmed)
        addCatchAnalysis(itemList)
        if (snapFishIsActivated())
            addSnapSummary(itemList, inboxSnaps, echogramInfos)
        return itemList
    }


    fun refreshOverviewItems() {
        toolRepository.refreshFishingFacilityChanges()
        snapRepository.refreshInboxContent()
        snapRepository.refreshEchogramListContent()
    }

    private fun addMapSummary(list : ArrayList<OverviewCardItem>) { //context.getString(R.string.overview_card_map_title)
        val item = OverviewCardItem("Map", "View a map with resources", R.drawable.ic_map, "", "View map", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_map, null)
        list.add(item)
    }

    private fun addSnapSummary(list : ArrayList<OverviewCardItem>,
                               inboxSnaps : LiveData<List<SnapMessage>>,
                               echogramInfos : LiveData<List<SnapMetadata>>) {
        //SnapRepository.getInstance(getApplication()).inboxSnaps.value.size

        val unread = inboxSnaps.value?.count{!it.seen} ?: 0
        val echogramCount = echogramInfos.value?.size ?: 0
        //TODO find number of unread messages and number of new snaps,  and add these numbers to the messsage
        val item = OverviewCardItem("SnapFish", "Share echo sounder snaps with your contacts", R.drawable.ic_snap, "You have $unread unread snap messages.\nYou have $echogramCount snaps to share.", "View inbox", "Send snap")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_snap, null)
        list.add(item)
    }

    private fun addCatchAnalysis(list : ArrayList<OverviewCardItem>) {
        val item = OverviewCardItem("Catch", "View and analyse catch history", R.drawable.ic_chart, "Updated with data for January 2020.", "View catch analysis", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_analysis, null)
        list.add(item)
    }

    private fun isProfileValid():Boolean {
        return isUserProfileValid(getApplication())
    }

    private fun hasToolDeploymentRights():Boolean {
        return true
    }

    private fun snapFishIsActivated():Boolean {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        var active= prefs.getBoolean(context.getString(R.string.pref_snap_enable_service), false)

        return active
    }

    private fun addToolsSummary(list : ArrayList<OverviewCardItem>, confirmed : LiveData<List<FishingFacility>>, unconfirmed : LiveData<List<FishingFacility>>) {
        if (isProfileValid()) {
            val numConfirmed = confirmed?.value?.size ?: 0
            val numUnconfirmed = unconfirmed?.value?.size ?: 0

            var description = "You have $numConfirmed confirmed and $numUnconfirmed unconfirmed tools"
            val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, description, "View reports", "Report new tool")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_tools, null)
            item.action2Listener = Navigation.createNavigateOnClickListener(R.id.deployment_editor_fragment, null)
            list.add(item)
        } else {
            val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, "To use this feature, contact person, phone and email must first be filled in preferences.", "Edit preferences", "")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_preferences, null)
            list.add(item)
        }
    }

}
