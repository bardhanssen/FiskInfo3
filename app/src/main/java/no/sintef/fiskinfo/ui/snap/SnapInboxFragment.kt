/**
 * Copyright (C) 2019 SINTEF
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
package no.sintef.fiskinfo.ui.snap

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.model.SnapMessage

/**
 * A fragment showing the inbox of snap messages.
 *
 *
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class SnapInboxFragment : Fragment(), SnapRecyclerViewAdapter.OnSnapInteractionListener {
     private var mViewModel: SnapViewModel? = null
    private var mAdapter: SnapRecyclerViewAdapter? = null
    private var mSwipeLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(SnapViewModel::class.java)
        mViewModel!!.getInboxSnaps()!!.observe(this,
            Observer { snaps ->
                mAdapter!!.setSnaps(snaps)
                if (mSwipeLayout != null)
                    mSwipeLayout!!.isRefreshing = false
            })

        /*        ViewParent parent = this.getView().getParent();
        if (parent instanceof ViewPager) {
            TabLayout tabLayout = (TabLayout) ((ViewPager) parent).findViewById(R.id.snaptab_layout);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.snap_inbox_fragment, container, false)

        val listView = view.findViewById<RecyclerView>(R.id.inbox_list)
        val context = view.context
        listView.setLayoutManager(LinearLayoutManager(context))
        mAdapter = SnapRecyclerViewAdapter(this)
        listView.setAdapter(mAdapter)

        mSwipeLayout = view.findViewById(R.id.inboxswipelayout) as SwipeRefreshLayout
        //swipeLayout.setProgressBackgroundColorSchemeResource(R.color.colorBrn);

        mSwipeLayout!!.setOnRefreshListener { mViewModel!!.refreshInboxContent() }


        /*        if (container instanceof ViewPager) {
               TabLayout tabLayout = (TabLayout) ((ViewPager) container).findViewById(R.id.snaptab_layout);
               tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
               View customTab = inflater.inflate(R.layout.tab_with_icon_and_title, container, false);
               tabLayout.getTabAt(1).setCustomView(customTab);
               TextView tabText = customTab.findViewById(R.id.tabTextView);
               tabText.setText("Inbox");
           }*/
        return view
    }

    override fun onViewSnapClicked(v: View, snap: SnapMessage?) {
        mViewModel!!.selectSnap(snap)
        Navigation.findNavController(v).navigate(R.id.action_fragment_snap_to_snapDetailFragment)
    }

    override fun onViewSnapInMapClicked(v: View, snap: SnapMessage?) {
        val toast = Toast.makeText(this.context, "Not yet implemented!", Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {

        fun newInstance(columnCount: Int): SnapInboxFragment {
            return SnapInboxFragment()
        }
    }
}


/*package no.sintef.fiskinfo.ui.snap

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import no.sintef.fiskinfo.R

class SnapInboxFragment : Fragment() {

    companion object {
        fun newInstance() = SnapInboxFragment()
    }

    private lateinit var viewModel: SnapInboxViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.snap_inbox_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SnapInboxViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
*/