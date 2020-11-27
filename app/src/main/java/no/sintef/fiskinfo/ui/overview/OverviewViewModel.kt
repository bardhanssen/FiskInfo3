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
import java.util.*
import kotlin.collections.ArrayList

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    val toolRepository = FishingFacilityRepository(application)
    val snapRepository = SnapRepository.getInstance(application)

    val overviewInfo = initOverviewInfo()
    var overviewList = MutableLiveData<List<OverviewCardItem>>()

    private fun initOverviewInfo(): MutableLiveData<OverviewInfo> {
        val overview = MediatorLiveData<OverviewInfo>()

        val confirmedTools = toolRepository.getConfirmedTools()
        val unconfirmedTools = toolRepository.getUnconfirmedTools()

        val inboxSnaps = snapRepository.getInboxSnaps()
        val echogramInfos = snapRepository.getEchogramInfos()

        overview.addSource(confirmedTools) { value ->
            overview.value?.numConfirmedTools = value.size
            overview.postValue(overview.value!!)
        }
        overview.addSource(unconfirmedTools) { value ->
            overview.value?.numUnconfirmedTools = value.size
            overview.postValue(overview.value!!)
        }
        overview.addSource(inboxSnaps) { value ->
            overview.value?.numInboxMsg = value.size
            overview.value?.numUnreadMsg = value.count{!it.seen}
            overview.postValue(overview.value!!)
        }
        overview.addSource(echogramInfos) { value ->
            overview.value?.numEchogram = value.size
            overview.postValue(overview.value!!)
        }
        overview.value = OverviewInfo()
        return overview
    }

    /*
    private fun refreshFromPreferences() {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        var irccontactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        var contactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")

    } */

    fun updateOverviewCardItems(context: Context) //:ArrayList<OverviewCardItem>
    {
        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList, context)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList, context)
        addCatchAnalysis(itemList, context)
        if (snapFishIsActivated())
            addSnapSummary(itemList, context)
        overviewList.value = itemList
    }


    fun refreshOverviewItems() {
        toolRepository.refreshFishingFacilityChanges()
        snapRepository.refreshInboxContent()
        snapRepository.refreshEchogramListContent()
    }

    private fun addMapSummary(list : ArrayList<OverviewCardItem>, context: Context) { //context.getString(R.string.overview_card_map_title)
        val item = OverviewCardItem(context.getString(R.string.overview_card_map_title), context.getString(
                    R.string.overview_card_map_subtitle), R.drawable.ic_map, "", context.getString(R.string.overview_card_view_map), "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_map, null)
        list.add(item)
    }

    private fun addSnapSummary(list : ArrayList<OverviewCardItem>, context: Context) {
                               //inboxSnaps : LiveData<List<SnapMessage>>,
                               //echogramInfos : LiveData<List<SnapMetadata>>) {
        //SnapRepository.getInstance(getApplication()).inboxSnaps.value.size

        val unread = overviewInfo.value?.numUnreadMsg // inboxSnaps.value?.count{!it.seen} ?: 0
        val echogramCount = overviewInfo.value?.numEchogram //echogramInfos.value?.size ?: 0

        //TODO find number of unread messages and number of new snaps,  and add these numbers to the messsage
        val item = OverviewCardItem(context.getString(R.string.overview_card_snap_title), context.getString(
                    R.string.overview_card_snap_subtitle), R.drawable.ic_snap,
                context.getString(R.string.overview_card_snap_description).format(unread, echogramCount), context.getString(
                                R.string.overview_card_snap_view_messages), context.getString(R.string.overview_card_snap_send_snap))
//        val item = OverviewCardItem("SnapFish", "Share echo sounder snaps with your contacts", R.drawable.ic_snap, "You have $unread unread snap messages.\nYou have $echogramCount snaps to share.", "View inbox", "Send snap")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_snap, null)
        list.add(item)
    }

    private fun addCatchAnalysis(list : ArrayList<OverviewCardItem>, context: Context) {
        val item = OverviewCardItem(context.getString(R.string.overview_card_analysis_title),
            context.getString(R.string.overview_card_analysis_subtitle), R.drawable.ic_chart,
            context.getString(R.string.overview_card_analysis_description), //.format(overviewInfo.value?.catchDataLastUpdated),
            context.getString(R.string.overview_card_analysis_view_analysis), "")
//        val item = OverviewCardItem("Catch", "View and analyse catch history", R.drawable.ic_chart, "Updated with data for January 2020.", "View catch analysis", "")
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

    private fun addToolsSummary(list : ArrayList<OverviewCardItem>, context: Context) { //confirmed : LiveData<List<FishingFacility>>, unconfirmed : LiveData<List<FishingFacility>>) {
        if (isProfileValid()) {
            val numConfirmed = overviewInfo.value?.numConfirmedTools // confirmed?.value?.size ?: 0
            val numUnconfirmed = overviewInfo.value?.numUnconfirmedTools //unconfirmed?.value?.size ?: 0

            val item = OverviewCardItem(context.getString(R.string.overview_card_tools_title),
                context.getString(R.string.overview_card_tools_subtitle), R.drawable.ic_hook,
                context.getString(R.string.overview_card_tools_description).format(numConfirmed, numUnconfirmed),
                context.getString(R.string.overview_card_tools_view_tools),
                context.getString(R.string.overview_card_tools_new_tool))

            //var description = "You have $numConfirmed confirmed and $numUnconfirmed unconfirmed tools"
            //val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, description, "View reports", "Report new tool")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_tools, null)
            item.action2Listener = Navigation.createNavigateOnClickListener(R.id.deployment_editor_fragment, null)
            list.add(item)
        } else {
            val item = OverviewCardItem(context.getString(R.string.overview_card_tools_title),
                context.getString(R.string.overview_card_tools_subtitle), R.drawable.ic_hook,
                context.getString(R.string.overview_card_tools_description_missing_info),
                context.getString(R.string.overview_card_tools_edit_preferences), "")
            //val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, "To use this feature, contact person, phone and email must first be filled in preferences.", "Edit preferences", "")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_preferences, null)
            list.add(item)
        }
    }

    data class OverviewInfo(
        var numConfirmedTools: Int = 0,
        var numUnconfirmedTools: Int = 0,
        var catchDataLastUpdated: String = "September 2020",
        var numInboxMsg: Int = 0,
        var numUnreadMsg: Int = 0,
        var numEchogram: Int = 0
    )

}
