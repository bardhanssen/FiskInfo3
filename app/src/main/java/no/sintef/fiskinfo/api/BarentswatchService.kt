package no.sintef.fiskinfo.api

import no.sintef.fiskinfo.model.EchogramInfo
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.model.barentswatch.PropertyDescription
import no.sintef.fiskinfo.model.barentswatch.Subscription
import no.sintef.fiskinfo.model.barentswatch.SubscriptionSubmitObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interface to REST API services from Barentswatch
 */
/*
object BarentswatchServicePaths {
    val subscribable = "service/subscribable/"
    val geoDataSubscription = "subscription/"
    val geoDataSubscriptionManagement = "subscription/{Id}"
    val geoDataDownload = "download/{ApiName}"
    val authorization = "authorization"
}
*/
interface BarentswatchService {
    companion object {
        const val prefix = "api/v1/geodata/"
        const val subscribable = prefix + "service/subscribable/"
        const val geoDataSubscription = prefix + "subscription/"
        const val geoDataSubscriptionManagement = prefix + "subscription/{Id}"
        const val geoDataDownload = prefix + "download/{ApiName}"
        const val authorization = prefix + "authorization"
    }

    @GET( subscribable)
    fun getSubscribable(): Call<List<PropertyDescription>>
    //    @GET("users/{user}/repos")
    //    Call<List<SnapMessage>> listRepos(@Path("user") String user);

    @GET(geoDataSubscription)
    fun getSubscriptions(): Call<List<Subscription>>

    @POST(geoDataSubscription)
    fun setSubscription(@Body subscription: SubscriptionSubmitObject): Call<Subscription>

}
