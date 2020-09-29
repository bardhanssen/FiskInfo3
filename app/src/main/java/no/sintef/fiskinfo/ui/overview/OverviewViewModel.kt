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

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    /*
    val userName = ""
    val passWord = ""

    var token : Token? = null

    private val barentsWatchProdAddress = "https://www.barentswatch.no/"
    fun testLogin() {
        try {
            val cred_type_pw = "password"
            val cred_type_client = "client_credentials"

            var loginClient : BarentswatchTokenService = createService(BarentswatchTokenService::class.java, barentsWatchProdAddress)
            var tokenRequest = loginClient.requestToken(cred_type_pw, userName, passWord)
            var token = tokenRequest.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    token = response.body()
                    testNextCall()
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    t.printStackTrace()
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        //var bwService : BarentswatchService = BasicAuthClient<BarentswatchService>(accessToken = ).create()


    }

    fun testNextCall() {
        var serviceClient : BarentswatchService = createService(BarentswatchService::class.java, barentsWatchProdAddress, token!!.access_token)
        var subRequest = serviceClient.getSubscriptions()
        var token = subRequest.enqueue(object : Callback<List<Subscription>> {
            override fun onResponse(call: Call<List<Subscription>>, response: Response<List<Subscription>>) {
                response.body()?.size
            }

            override fun onFailure(call: Call<List<Subscription>>, t: Throwable) {
                t.printStackTrace()
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

*/

    var overviewList = MutableLiveData<List<OverviewCardItem>>()

/*
    private fun refreshFromPreferences() {
        var context : Context = getApplication()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        var irccontactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")
        var contactPersonEmail = prefs.getString(context.getString(R.string.pref_contact_person_email), "")

    }

*/
    fun getOverviewItems() : MutableLiveData<List<OverviewCardItem>> {
/*        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList)
        addCatchAnalysis(itemList)
        if (snapFishIsActivated())
            addSnapSummary(itemList)
        overviewList.value = itemList/
 */
        refreshOverviewItems()
        return overviewList
    }

    fun refreshOverviewItems() {
//        refreshFromPreferences()
        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        if (hasToolDeploymentRights())
            addToolsSummary(itemList)
        addCatchAnalysis(itemList)
        if (snapFishIsActivated())
            addSnapSummary(itemList)
        overviewList.value = itemList

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

    private fun addToolsSummary(list : ArrayList<OverviewCardItem>) {
        if (isProfileValid()) {
            val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, "", "View reports", "Report new tool")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_tools, null)
            // TODO: Add parameter and initialize to empty viewmodel when navigating to editor
            item.action2Listener = Navigation.createNavigateOnClickListener(R.id.deployment_editor_fragment, null)
            list.add(item)
        } else {
            val item = OverviewCardItem("Tools", "Create and view tool deployment reports", R.drawable.ic_hook, "To use this feature, contact person, phone and email must first be filled in preferences.", "Edit preferences", "")
            item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_preferences, null)
            list.add(item)
        }
    }

}
