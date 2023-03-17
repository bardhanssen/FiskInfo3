package no.sintef.fiskinfo.ui.sprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.SpriceFragmentBinding
import no.sintef.fiskinfo.ui.tools.ToolListFragment

/**
 * A simple [Fragment] subclass.
 * Use the [SpriceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint // Dagger-Hilt requirement
class SpriceFragment : Fragment() {

    companion object {
        fun newInstance() = SpriceFragment()
    }

    private lateinit var viewModel: SpriceViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var pageAdapter: IcingReportPageAdapter

    private var _binding: SpriceFragmentBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SpriceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pageAdapter = IcingReportPageAdapter(childFragmentManager)
        viewPager = binding.spriceViewPager
        viewPager.adapter = pageAdapter
        val tabLayout = binding.spriceReportsTabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class IcingReportPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? {
            return resources.getStringArray(R.array.tool_tab_titles)[position]
        }

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                SpriceIcingReportListFragment.newInstance()
//                UnconfirmedToolsFragment.newInstance() //true)
            }
            else {
                SpriceIcingReportListFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }

}
