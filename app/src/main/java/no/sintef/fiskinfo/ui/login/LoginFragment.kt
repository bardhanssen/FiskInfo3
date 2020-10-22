/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import net.openid.appauth.*
import no.sintef.fiskinfo.BuildConfig
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.util.AuthStateManager


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
    private lateinit var loginButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authStateManager = AuthStateManager.getInstance(this.requireContext())

        loginButton = view.findViewById(R.id.sign_in_button)
        loginButton.setOnClickListener {
            startAuthentication()
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
                    val imm =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION ->
                    Snackbar.make(
                        view,
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
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val consent = prefs.getBoolean("user_consent_to_terms", false)
        if (!consent)
            Navigation.findNavController(this.requireView()).navigate(R.id.consentFragment)
        else {
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

                        startActivityForResult(intent, REQCODE_AUTH)
                    } else {
                        if (ex != null) {
                            val m = Throwable().stackTrace[0]
                            //Log.e(LOG_TAG, "${m}: ${ex}")
                        }
                        whenAuthorizationFails(ex)
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQCODE_AUTH) {
            if (resultCode == Activity.RESULT_OK) {
                val resp = AuthorizationResponse.fromIntent(data!!)
                val ex = AuthorizationException.fromIntent(data)

                authStateManager.updateAfterAuthorization(resp, ex)

                val clientAuth: ClientAuthentication = ClientSecretBasic(CLIENT_SECRET)
                mAuthService
                    .performTokenRequest(
                        resp!!.createTokenExchangeRequest(),
                        clientAuth,
                        { resp2, ex2 -> // TODO: Investigae possible null pointer exception on this line
                            if (resp2 != null) {

                                authStateManager.updateAfterTokenResponse(resp2, ex2)
                                viewModel.authenticate()

                            } else {
                                whenAuthorizationFails(ex2)
                            }
                        })

            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                val ex = AuthorizationException.fromIntent(data)
                // TODO: Handle this
            }
        }
    }

    private fun whenAuthorizationFails(ex: AuthorizationException?) {
        // TODO: Implement feedback when authorization fails
        //uResponseView.text = "%s\n\n%s".format(getText(R.string.msg_auth_ng), ex?.message)
        //doShowAppAuthState()
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
