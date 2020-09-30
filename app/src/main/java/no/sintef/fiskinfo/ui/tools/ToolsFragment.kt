/**
 * Copyright (C) 2020 SINTEF
 *
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
package no.sintef.fiskinfo.ui.tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import no.sintef.fiskinfo.R

class ToolsFragment : Fragment() {

    companion object {
        fun newInstance() = ToolsFragment()
    }

    private lateinit var viewModel: ToolsViewModel
    internal lateinit var viewPager: ViewPager
    internal lateinit var pageAdapter: ToolPageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tools_fragment, container, false)
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(ToolsViewModel::class.java)
        // TODO: Use the ViewModel
    }
*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pageAdapter = ToolPageAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.toolspager) //TODO
        viewPager.adapter = pageAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.toolstab_layout) //TODO
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class ToolPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? {
            return resources.getStringArray(R.array.tool_tab_titles)[position]
        }

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                ToolListFragment.newInstance(false)
//                UnconfirmedToolsFragment.newInstance() //true)
            }
            else {
                ToolListFragment.newInstance(true)
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

}
