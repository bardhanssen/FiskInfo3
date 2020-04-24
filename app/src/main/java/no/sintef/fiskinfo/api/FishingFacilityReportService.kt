package no.sintef.fiskinfo.api

import com.google.gson.JsonElement
import no.sintef.fiskinfo.model.fishingfacility.FishingFacilityChanges
import no.sintef.fiskinfo.model.fishingfacility.FiskInfoProfileDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Documentation of API:  https://code.barentswatch.net/openapi/?url=https://www.barentswatch.no/api/documentation/fiskinfo-reporting.yaml

interface FishingFacilityReportService {
    companion object {
        const val v1prefix = "bwapi/v1/geodata/"
        const val v2prefix = "bwapi/v2/geodata/"
        const val fishingfacilityprofile = v1prefix + "fishingfacilityprofile"
        const val fishingfacilitychanges = v2prefix + "fishingfacilitychanges"
        //const val geoDataSubscription = prefix + "subscription/"
        //const val geoDataSubscriptionManagement = prefix + "subscription/{Id}"
        //const val geoDataDownload = prefix + "download/{ApiName}"
        const val authorization = v1prefix + "authorization"
    }

    @GET(fishingfacilityprofile)
//    fun getFishingFacilityProfile(): Call<JsonElement>
    fun getFishingFacilityProfile(): Call<FiskInfoProfileDTO>

    @GET(fishingfacilitychanges)
    fun getFishingFacilityChanges(): Call<FishingFacilityChanges>


    /*
    @GET(geoDataSubscription)
    fun getSubscriptions(): Call<List<Subscription>>

    @POST(geoDataSubscription)
    fun setSubscription(@Body subscription: SubscriptionSubmitObject): Call<Subscription>*/


}