package no.sintef.fiskinfo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
/*
import net.openid.appauth.AuthState
import no.sintef.fiskinfo.api.BarentswatchTokenService
import no.sintef.fiskinfo.api.createService
import no.sintef.fiskinfo.model.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
*/

class LoginViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED,          // Initial state, the user needs to authenticate
        UNAUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    val authenticationState = MutableLiveData<AuthenticationState>()
//    var username = ""

    init {
        // In this example, the user is always unauthenticated when MainActivity is launched
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
//        username = ""
    }

    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate() {
        authenticationState.value = AuthenticationState.AUTHENTICATED
    }

    //var count = 0
/*    var token : Token? = null
    lateinit var appAuthState: AuthState

    private val barentsWatchProdAddress = "https://www.barentswatch.no/"

    fun updateFromAppAuthState() {
        if (appAuthState.isAuthorized && (appAuthState.accessToken != null)) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
        }
    }
*/
/*
    fun authenticate(username: String, password: String) {
        try {
            val cred_type_pw = "password"
            val cred_type_client = "client_credentials"

            var loginClient : BarentswatchTokenService = createService(BarentswatchTokenService::class.java, barentsWatchProdAddress)
            var tokenRequest = loginClient.requestToken(cred_type_pw, username, password)
            tokenRequest.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    token = response.body()
                    if (token != null) {
                        authenticationState.value = AuthenticationState.AUTHENTICATED
                    }
                    else {
                        authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                    t.printStackTrace()
                }
            })
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
        //var bwService : BarentswatchService = BasicAuthClient<BarentswatchService>(accessToken = ).create()



/        count++
        if (count > 2) { //passwordIsValidForUsername(username, password)) {
            this.username = username
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
        }/
    }

    */
    // TODO: Implement the ViewModel
}
