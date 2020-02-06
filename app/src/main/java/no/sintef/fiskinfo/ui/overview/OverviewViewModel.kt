package no.sintef.fiskinfo.ui.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.api.*
import no.sintef.fiskinfo.model.Token
import no.sintef.fiskinfo.model.barentswatch.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {
    // TODO: Implement the ViewModel

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

    var overviewList = MutableLiveData<List<OverviewCardItem>>()


    fun getOverViewItems() : MutableLiveData<List<OverviewCardItem>> {
        if (overviewList.value == null) {
            val itemList = ArrayList<OverviewCardItem>()
            addMapSummary(itemList)
            addSnapSummary(itemList)
            addCatchAnalysis(itemList)
            overviewList.value = itemList
        }
        return overviewList
    }

    private fun addMapSummary(list : ArrayList<OverviewCardItem>) {
        val item = OverviewCardItem("Map", "View a map with resources", R.drawable.ic_map, "", "View map", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_map, null)
        list.add(item)
    }

    private fun addSnapSummary(list : ArrayList<OverviewCardItem>) {
        val item = OverviewCardItem("SnapFish", "Share echo sounder snaps with your contacts", R.drawable.ic_snap, "You have 5 unread snap messages. You have 1 new snap to share", "View inbox", "Send snap")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_snap, null)
        list.add(item)
    }
    private fun addCatchAnalysis(list : ArrayList<OverviewCardItem>) {
        val item = OverviewCardItem("Catch", "View and analyse catch history", R.drawable.ic_chart, "Updated with data for January 2020.", "View catch analysis", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_analysis, null)
        list.add(item)
    }


}
