package no.sintef.fiskinfo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.openid.appauth.AuthorizationService
import no.sintef.fiskinfo.api.FishingFacilityReportService
import no.sintef.fiskinfo.api.createService
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.model.fishingfacility.FishingFacilityChanges
import no.sintef.fiskinfo.util.AuthStateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FishingFacilityRepository(context: Context) {
    internal var fishingFacilityService: FishingFacilityReportService? = null
    internal var snapFishServerUrl: String? = DEFAULT_SNAP_FISH_SERVER_URL


    internal var confirmedTools = MutableLiveData<List<FishingFacility>>()
    internal val unconfirmedTools = MutableLiveData<List<FishingFacility>>()

    internal val authStateManager = AuthStateManager.getInstance(context)
    internal val authService = AuthorizationService(context)

    val bwServerUrl = "https://pilot.barentswatch.net/"

    init {
        updateFromPreferences(context)
    }

    fun updateFromPreferences(context: Context) {}


    fun getConfirmedTools(): LiveData<List<FishingFacility>> {
        refreshFishingFacilityChanges()
        return confirmedTools
    }


    fun getUnconfirmedTools(): LiveData<List<FishingFacility>> {
        refreshFishingFacilityChanges()
        return unconfirmedTools
    }

    fun initService() {
        fishingFacilityService =
            createService(FishingFacilityReportService::class.java,bwServerUrl , authService, authStateManager.current)
    }

    fun refreshFishingFacilityChanges() {
        if (fishingFacilityService == null)
            initService()


//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        fishingFacilityService?.getFishingFacilityChanges()?.enqueue(object : Callback<FishingFacilityChanges> {
            override fun onResponse(call: Call<FishingFacilityChanges>, response: Response<FishingFacilityChanges>) {
                if (response.body() != null) {
                    confirmedTools.value = response.body()!!.confirmed_tools
                    unconfirmedTools.value = response.body()!!.unconfirmed_tools
                    // TODO: Reports?
                }
            }

            override fun onFailure(call: Call<FishingFacilityChanges>, t: Throwable) {
                confirmedTools.value = ArrayList()
                unconfirmedTools.value = ArrayList()
                // TODO: log problem?
            }
        })
    }

    companion object {
        var instance: FishingFacilityRepository? = null

        internal val DEFAULT_SNAP_FISH_SERVER_URL = "http://129.242.16.123:37789/"
        fun getInstance(context: Context): FishingFacilityRepository {
            if (instance == null)
                instance = FishingFacilityRepository(context)
            return instance!!
        }
    }
}