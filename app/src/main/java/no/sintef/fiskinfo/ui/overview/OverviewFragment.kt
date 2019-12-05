package no.sintef.fiskinfo.ui.overview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.ui.login.LoginViewModel

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var viewModel: OverviewViewModel

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //welcomeTextView = view.findViewById(R.id.welcome_text_view)

        val navController = findNavController()
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.login_fragment)
            }
        })

        view.findViewById<Button>(R.id.map_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_map, null))
        view.findViewById<Button>(R.id.snapfish_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_snap, null))
        view.findViewById<Button>(R.id.catch_analysis_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_analysis, null))
    }

    private fun showWelcomeMessage() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OverviewViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
