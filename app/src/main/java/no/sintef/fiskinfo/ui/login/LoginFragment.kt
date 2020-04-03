package no.sintef.fiskinfo.ui.login

import android.content.Context
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
import net.openid.appauth.*
import no.sintef.fiskinfo.R


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

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText = view.findViewById(R.id.email_sign_in_edit_text)
        passwordEditText = view.findViewById(R.id.password_sign_in_edit_text)

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

    //val ISSUER_URI = "https://id.barentswatch.net/"
    //val CLIENT_ID = "sinteffiskinfoandroidapp"
    //val REDIRECT_URI = "no.sintef.fiskinfo.android://"
    //val SCOPE = "api openid"
    //val REQCODE_AUTH = 100

    val ISSUER_URI = "https://id.barentswatch.net/"
    val CLIENT_ID = "sinteffiskinfoapp"
    val REDIRECT_URI = "iOSFiskInfoApp://"
    val SCOPE = "api openid offline_access"
    val REQCODE_AUTH = 100


    fun startAuthentication() {

        AuthorizationServiceConfiguration.fetchFromIssuer(Uri.parse(ISSUER_URI),
            fun(config: AuthorizationServiceConfiguration?, ex: AuthorizationException?) {
                if (config != null) {
                    val authState = AuthState(config) // TODO: Store?


                    val req = AuthorizationRequest
                        .Builder(
                            config,
                            CLIENT_ID,
                            ResponseTypeValues.CODE,
                            Uri.parse(REDIRECT_URI)
                        )
                        .setScope(SCOPE)
                        .build()
                    val intent =
                        AuthorizationService(this.requireActivity()).getAuthorizationRequestIntent(req)

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


        /*
        val serviceConfig: AuthorizationServiceConfiguration =
        AuthorizationServiceConfiguration.fetchFromIssuer(
            Uri.parse("https://idp.example.com"), { serviceConfiguration, ex ->
                if (ex != null) {
                    // Log.e(TAG, "failed to fetch configuration")
                    return@RetrieveConfigurationCallback
                }
                // use serviceConfiguration as needed
            } )*/
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

}
