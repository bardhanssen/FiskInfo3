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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.ui.login.LoginViewModel


class OverviewFragment : Fragment(), OverviewRecyclerViewAdapter.OnOverviewCardInteractionListener {
    override fun onAction2Clicked(v: View, item: OverviewCardItem?) {
        item?.action2Listener?.onClick(v);
    }

    override fun onAction1Clicked(v: View, item: OverviewCardItem?) {
        item?.action1Listener?.onClick(v);
    }

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private lateinit var viewModel: OverviewViewModel

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.login_fragment)
            }
        })

    }

    private fun showWelcomeMessage() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.overview_fragment, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.overview_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = OverviewRecyclerViewAdapter(this)
        recyclerView.adapter = mAdapter

        return view
    }

    private var mAdapter: OverviewRecyclerViewAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OverviewViewModel::class.java)
        viewModel!!.getOverViewItems().observe(this, Observer { mAdapter?.setOverviewItems(it) })
    }

}
