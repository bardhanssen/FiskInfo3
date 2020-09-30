package no.sintef.fiskinfo.api

import com.google.gson.JsonElement
import no.sintef.fiskinfo.model.fishingfacility.DeploymentInfo
import no.sintef.fiskinfo.model.fishingfacility.FishingFacilityChanges
import no.sintef.fiskinfo.model.fishingfacility.FiskInfoProfileDTO
import no.sintef.fiskinfo.model.fishingfacility.RetrievalInfoDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Documentation of API:  https://code.barentswatch.net/openapi/?url=https://www.barentswatch.no/api/documentation/fiskinfo-reporting.yaml

interface FishingFacilityReportService {
    companion object {
        const val v1prefix = "bwapi/v1/geodata/"
        const val v2prefix = "bwapi/v2/geodata/"
        const val fishingfacilityprofile = v1prefix + "fishingfacilityprofile"
        const val fishingfacilitychanges = v2prefix + "fishingfacilitychanges"
        const val deployed = v1prefix + "fishingfacilitychange/deployed"
        const val retrieved = v1prefix + "fishingfacilitychange/retrieved"
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

    @POST(deployed)
    fun sendDeploymentInfo(@Body deploymentInfo: DeploymentInfo):Call<Void?> // Call<JsonElement?>

    @POST(retrieved)
    fun sendRetrieved(@Query("toolId") toolId: String, @Body retrievalInfo: RetrievalInfoDto):Call<Void?>

    /*
    @GET(geoDataSubscription)
    fun getSubscriptions(): Call<List<Subscription>>

    @POST(geoDataSubscription)
    fun setSubscription(@Body subscription: SubscriptionSubmitObject): Call<Subscription>*/


}