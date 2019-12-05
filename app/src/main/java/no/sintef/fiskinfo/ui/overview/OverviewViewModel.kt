package no.sintef.fiskinfo.ui.overview

import androidx.lifecycle.ViewModel;
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
}
