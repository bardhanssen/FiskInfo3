package no.sintef.fiskinfo.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement
import net.openid.appauth.*
import no.sintef.fiskinfo.BuildConfig
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.api.BarentswatchService
import no.sintef.fiskinfo.api.FishingFacilityReportService
import no.sintef.fiskinfo.api.createService
import no.sintef.fiskinfo.model.barentswatch.PropertyDescription
import no.sintef.fiskinfo.model.barentswatch.Subscription
import no.sintef.fiskinfo.model.fishingfacility.FishingFacilityChanges
import no.sintef.fiskinfo.model.fishingfacility.FiskInfoProfileDTO
import no.sintef.fiskinfo.util.AuthStateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    private val viewModel: LoginViewModel by activityViewModels()

    private lateinit var mAuthService : AuthorizationService
    private lateinit var authStateManager : AuthStateManager


    //private lateinit var usernameEditText: EditText
    //private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //usernameEditText = view.findViewById(R.id.email_sign_in_edit_text)
        //passwordEditText = view.findViewById(R.id.password_sign_in_edit_text)

        authStateManager = AuthStateManager.getInstance(this.requireContext())

        loginButton = view.findViewById(R.id.sign_in_button)
        loginButton.setOnClickListener {
            startAuthentication()
            //viewModel.authenticate(usernameEditText.text.toString(),
            //    passwordEditText.text.toString())
        }

        val navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.refuseAuthentication()
            //TODO: navController.popBackStack(R.id.main_fragment, false)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    navController.popBackStack()
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION ->
                    Snackbar.make(view,
                        R.string.login_error_incorrect_password,
                        Snackbar.LENGTH_SHORT
                    ).show()
            }
        })
    }

    val ISSUER_URI = "https://id.barentswatch.net/"
    val CLIENT_ID = BuildConfig.FISKINFO_BW_CLIENT_ID;
    val REDIRECT_URI = "no.sintef.fiskinfo.android://"
    val SCOPE = "api openid offline_access"
    val REQCODE_AUTH = 100
    val CLIENT_SECRET = BuildConfig.FISKINFO_BW_CLIENT_SECRET;

    //val ISSUER_URI = "https://id.barentswatch.net/"
    //val CLIENT_ID = "sinteffiskinfoapp"
    //val REDIRECT_URI = "iOSFiskInfoApp://"
    //val SCOPE = "api openid offline_access"
    //val REQCODE_AUTH = 100
    //val CLIENT_SECRET = ""


    fun startAuthentication() {

        AuthorizationServiceConfiguration.fetchFromIssuer(Uri.parse(ISSUER_URI),
            fun(config: AuthorizationServiceConfiguration?, ex: AuthorizationException?) {
                if (config != null) {
                    // man viewModel.appAuthState = AuthState(config) // TODO: Store?
                    //viewModel.appAuthState = authStateManager.current

                    val req = AuthorizationRequest
                        .Builder(
                            config,
                            CLIENT_ID,
                            ResponseTypeValues.CODE,
                            Uri.parse(REDIRECT_URI)
                        )
                        .setScope(SCOPE)
                        .build()

                    mAuthService = AuthorizationService(this.requireActivity())
                    val intent =
                        mAuthService.getAuthorizationRequestIntent(req)
//                    val intent =
//                        AuthorizationService(this.requireActivity()).getAuthorizationRequestIntent(req)

                    startActivityForResult(intent, REQCODE_AUTH)
                    //var serv = AuthorizationService(this.context!!);
                    //serv.
                } else {
                    if (ex != null) {
                        val m = Throwable().stackTrace[0]
                        //Log.e(LOG_TAG, "${m}: ${ex}")
                    }
                    whenAuthorizationFails(ex)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQCODE_AUTH) {
            if (resultCode == Activity.RESULT_OK) {
                val resp = AuthorizationResponse.fromIntent(data!!)
                val ex = AuthorizationException.fromIntent(data)

                //viewModel.appAuthState.update(resp, ex)
                authStateManager.updateAfterAuthorization(resp, ex)

                val clientAuth: ClientAuthentication = ClientSecretBasic(CLIENT_SECRET)
                //AuthorizationService(this.requireActivity())
                mAuthService
                    .performTokenRequest(resp!!.createTokenExchangeRequest(), clientAuth, { resp2, ex2 ->
                        //appAuthState.update(resp2, ex2)
                        if (resp2 != null) {

                            authStateManager.updateAfterTokenResponse(resp2, ex2)
                            //man viewModel.appAuthState.update(resp2, ex2)


                            // Login was successful, so return to previous fragment
                            //viewModel.updateFromAppAuthState()
                            viewModel.authenticate()

                            //val navController = findNavController()
                            //navController.popBackStack()


                            //testCall()
                            //testCall2()
                            //testCallProfile()
                            //testCallFacilityChanges()

                        } else {
                            whenAuthorizationFails(ex2)
                        }
                    })


                //handleAuthorizationResponse(data)
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                val ex = AuthorizationException.fromIntent(data)
                // TODO: Handle this
            }
        }
    }

    private fun whenAuthorizationFails(ex: AuthorizationException?) {
        //uResponseView.text = "%s\n\n%s".format(getText(R.string.msg_auth_ng), ex?.message)
        //doShowAppAuthState()
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

/*

    // Testing code

    val bwServerUrl = "https://pilot.barentswatch.net/"
    //val bwServerUrl = "https://pilot.barentswatch.net/bwapi/v1/geodata/service/subscribable/"

    private fun testCall() {
        val bwService =
            createService(BarentswatchService::class.java,bwServerUrl , mAuthService, viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        bwService.getSubscribable().enqueue(object : Callback<List<PropertyDescription>> {
            override fun onResponse(call: Call<List<PropertyDescription>>, response: Response<List<PropertyDescription>>) {
                response.body()
            }

            override fun onFailure(call: Call<List<PropertyDescription>>, t: Throwable) {
                t.stackTrace
                // TODO: log problem
            }
        })
    }

    private fun testCall2() {
        val bwService =
            createService(BarentswatchService::class.java,bwServerUrl , mAuthService, viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        bwService.getSubscriptions().enqueue(object : Callback<List<Subscription>> {
            override fun onResponse(call: Call<List<Subscription>>, response: Response<List<Subscription>>) {
                response.body()
            }

            override fun onFailure(call: Call<List<Subscription>>, t: Throwable) {
                t.stackTrace
                // TODO: log problem
            }
        })
    }



    // TODO: Test with other user profile
/*
    private fun testCallProfile() {
        val bwService =
            createService(FishingFacilityReportService::class.java,bwServerUrl , mAuthService, viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        bwService.getFishingFacilityProfile().enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                t.stackTrace
                // TODO: log problem
            }
        })
    }

*/

    private fun testCallProfile() {
        val bwService =
            createService(FishingFacilityReportService::class.java,bwServerUrl , mAuthService, viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        bwService.getFishingFacilityProfile().enqueue(object : Callback<FiskInfoProfileDTO> {
            override fun onResponse(call: Call<FiskInfoProfileDTO>, response: Response<FiskInfoProfileDTO>) {
                response.body()
            }

            override fun onFailure(call: Call<FiskInfoProfileDTO>, t: Throwable) {
                t.stackTrace
                // TODO: log problem
            }
        })
    }

    private fun testCallFacilityChanges() {
        val bwService =
            createService(FishingFacilityReportService::class.java,bwServerUrl , mAuthService, viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl , AuthorizationService(this.requireActivity()), viewModel.appAuthState)
//            createService(BarentswatchService::class.java,bwServerUrl )
        bwService.getFishingFacilityChanges().enqueue(object : Callback<FishingFacilityChanges> {
            override fun onResponse(call: Call<FishingFacilityChanges>, response: Response<FishingFacilityChanges>) {
                response.body()
            }

            override fun onFailure(call: Call<FishingFacilityChanges>, t: Throwable) {
                t.stackTrace
                // TODO: log problem
            }
        })
    }

*/

}
