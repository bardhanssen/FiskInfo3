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

        //welcomeTextView = view.findViewById(R.id.welcome_text_view)

        val navController = findNavController()
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.login_fragment)
            }
        })

//        view.findViewById<Button>(R.id.map_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_map, null))
//        view.findViewById<Button>(R.id.snapfish_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_snap, null))
//        view.findViewById<Button>(R.id.catch_analysis_button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragment_analysis, null))
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


    private fun getOverViewItems() : List<OverviewCardItem> {
        val itemList = ArrayList<OverviewCardItem>()
        addMapSummary(itemList)
        addSnapSummary(itemList)
        addCatchAnalysis(itemList)
        return itemList
    }

    private fun addMapSummary(list : ArrayList<OverviewCardItem>) {
        var item = OverviewCardItem("Map", "View a map with resources", R.drawable.ic_map,
            "This is a long description that could contain useful information in some cases.", "View map", "",
            Navigation.createNavigateOnClickListener(R.id.fragment_map, null),null )
        list.add(item)
    }

    private fun addSnapSummary(list : ArrayList<OverviewCardItem>) {
        var item = OverviewCardItem("SnapFish", "View a map with resources", R.drawable.ic_snap, "You have 5 unread snap messages. You have 1 new snap to share", "View inbox", "Send snap")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_snap, null)
        list.add(item)

    }
    private fun addCatchAnalysis(list : ArrayList<OverviewCardItem>) {
        var item = OverviewCardItem("Catch", "View and analyse catch history", R.drawable.ic_chart, "Updated with data for January 2020.", "View catch analysis", "")
        item.action1Listener = Navigation.createNavigateOnClickListener(R.id.fragment_analysis, null)
        list.add(item)
    }


}
