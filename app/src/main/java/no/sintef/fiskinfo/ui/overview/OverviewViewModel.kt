package no.sintef.fiskinfo.ui.overview

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.api.*
import no.sintef.fiskinfo.model.Token
import no.sintef.fiskinfo.model.barentswatch.Subscription
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.repository.FishingFacilityRepository
import no.sintef.fiskinfo.repository.SnapRepository
import no.sintef.fiskinfo.util.isUserProfileValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    val repository = FishingFacilityRepository(application)

    //var confirmedTools = FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools()
    //var unconfirmedTools = FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools()

//    var overviewList = MutableLiveData<List<OverviewCardItem>>()
/*    var overviewList = CombinedLiveData<List<OverviewCardItem>>(
        FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools(),
        FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools() ) { datas: List<Any?> -> refreshOverviewItems(datas) }
*/


    fun initOverviewItemsList(): MutableLiveData<List<OverviewCardItem>> {
        val overviewItemList = MediatorLiveData<List<OverviewCardItem>>()
        //val repo = FishingFacilityRepository.getInstance(getApplication())
        val confirmedTools = repository.getConfirmedTools()
        val unconfirmedTools = repository.getUnconfirmedTools()

        overviewItemList.addSource(confirmedTools) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools)
        }
        overviewItemList.addSource(unconfirmedTools) { value ->
            overviewItemList.value = refreshOverviewItems(confirmedTools, unconfirmedTools)
        }
        return overviewItemList
    }

    var overviewList = initOverviewItemsList() //MutableLiveData<List<OverviewCardItem>>()
    /*
    fun getOverviewItems():MutableLiveData<List<OverviewCardItem>>() {
        if (overviewItemList)
    }
*/

/*
            val itemList = ArrayList<OverviewCardItem>()
            addMapSummary(itemList)
            if (hasToolDeploymentRights())
                addToolsSummary(itemList)
            addCatchAnalysis(itemList)
            if (snapFishIsActivated())
                addSnapSummary(itemList)
            return@CombinedLiveData itemList

        // Use datas[0], datas[1], ..., datas[N] to return a SomeType value
         } )

*/
    /*
    private fun refreshFromPreferences() {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        var irccontactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        var contactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")

    }

*/
    /*
    fun getOverviewItems() : MutableLiveData<List<OverviewCardItem>> {
        refreshOverviewItems()
        return overviewList
    }
*/
    fun refreshOverviewItems(confirmed: LiveData<List<FishingFacility>>, unconfirmed: LiveData<List<FishingFacility>>):ArrayList<OverviewCardItem> {
        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList, confirmed, unconfirmed)
        addCatchAnalysis(itemList)
        if (snapFishIsActivated())
            addSnapSummary(itemList)
        return itemList
    }


    fun refreshOverviewItems() {
//        refreshFromPreferences()

        repository.refreshFishingFacilityChanges()
        //FishingFacilityRepository.getInstance(getApplication()).refreshFishingFacilityChanges()
/*        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList)
        addCatchAnalysis(itemList)
        if (snapFishIsActivated())
            addSnapSummary(itemList)
        overviewList.value = itemList
*/
    }

/*
    fun prepareItems() {
        var confirmedTools = FishingFacilityRepository.getInstance(getApplication()).getConfirmedTools()
        var unconfirmedTools = FishingFacilityRepository.getInstance(getApplication()).getUnconfirmedTools()

        confirmedTools.observe(getApplication(),
            Observer<List<FishingFacility>> { _tools ->
                mAdapter!!.setTools(_tools)
                if (mSwipeLayout != null)
                    mSwipeLayout!!.isRefreshing = false
            })


    }
*/


    private fun addMapSummary(list : ArrayList<OverviewCardItem>) {
        val item = OverviewCardItem("Map", "View a map with resources", R.drawable.ic_map, "", "View map", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_map, null)
        list.add(item)
    }

    private fun addSnapSummary(list : ArrayList<OverviewCardItem>) {
        //SnapRepository.getInstance(getApplication()).inboxSnaps.value.size

        val inboxSnaps = SnapRepository.getInstance(getApplication()).inboxSnaps;
        val echogramInfos = SnapRepository.getInstance(getApplication()).echogramInfos;
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



    /**
     * CombinedLiveData is a helper class to combine results from multiple LiveData sources.
     * @param liveDatas Variable number of LiveData arguments.
     * @param combine   Function reference that will be used to combine all LiveData data.
     * @param R         The type of data returned after combining all LiveData data.
     * Usage:
     * CombinedLiveData<SomeType>(
     *     getLiveData1(),
     *     getLiveData2(),
     *     ... ,
     *     getLiveDataN()
     * ) { datas: List<Any?> ->
     *     // Use datas[0], datas[1], ..., datas[N] to return a SomeType value
     * }
     */
/*    class CombinedLiveData<R>(vararg liveDatas: LiveData<*>,
                              private val combine: (datas: List<Any?>) -> R) : MediatorLiveData<R>() {

        private val datas: MutableList<Any?> = MutableList(liveDatas.size) { null }

        init {
            for(i in liveDatas.indices){
                super.addSource(liveDatas[i]) {
                    datas[i] = it
                    value = combine(datas)
                }
            }
        }
    }
*/

}
