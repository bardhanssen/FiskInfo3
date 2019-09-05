package no.sintef.fiskinfo.ui.snap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.SnapMetadata
import no.sintef.fiskinfo.repository.SnapRepository

/**
 * A fragment showing a list of Echograms.
 *
 *
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class EchogramListFragment : Fragment(), EchogramRecyclerViewAdapter.OnEchogramInteractionListener {

    private var mSnapViewModel: SnapViewModel? = null
    private var mEchogramViewModel: EchogramViewModel? = null
    private var mAdapter: EchogramRecyclerViewAdapter? = null
    private var mSwipeLayout: SwipeRefreshLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSnapViewModel = ViewModelProviders.of(activity!!).get(SnapViewModel::class.java)
        mEchogramViewModel = ViewModelProviders.of(activity!!).get(EchogramViewModel::class.java)
        mEchogramViewModel!!.getEchogramInfos()!!.observe(this,
            Observer { echogramInfos ->
                mAdapter!!.setEchograms(echogramInfos)
                if (mSwipeLayout != null)
                    mSwipeLayout!!.isRefreshing = false
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.echogram_list_fragment, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.echogram_list)

        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = EchogramRecyclerViewAdapter(this)
        recyclerView.adapter = mAdapter

        mSwipeLayout = view.findViewById<View>(R.id.echogramlistswipelayout) as SwipeRefreshLayout

        mSwipeLayout!!.setOnRefreshListener { mEchogramViewModel!!.refreshEchogramListContent() }

        return view
    }

    override fun onViewEchogramClicked(v: View, echogram: SnapMetadata?) {
        if (echogram?.snapId != null) {
            var snapId = echogram.snapId.toString()
            var bundle = bundleOf(ARG_SNAP_ID to snapId)
            v.findNavController().navigate(R.id.action_fragment_snap_to_echogram_viewer_fragment, bundle)
        }
    }

    override fun onShareEchogramClicked(v: View, echogram: SnapMetadata?) {
        mSnapViewModel!!.createDraftFrom(echogram!!)
        Navigation.findNavController(v).navigate(R.id.action_snap_fragment_to_newSnapFragment)
    }

    companion object {
        fun newInstance() = EchogramListFragment()
    }
}

