package no.sintef.fiskinfo.ui.sprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.dal.sprice.SpriceDbRepository
import no.sintef.fiskinfo.databinding.SpriceFragmentBinding
import no.sintef.fiskinfo.model.sprice.enums.SpriceReportListTypeEnum
import javax.inject.Inject

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

    @Inject
    lateinit var spriceDbRepository: SpriceDbRepository

    private var NUMBER_OF_PAGES = 2
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var viewModel: SpriceViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var pageAdapter: ScreenSlidePagerAdapter

    private var _binding: SpriceFragmentBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        _binding = SpriceFragmentBinding.inflate(inflater, container, false)
        viewPager = binding.spriceViewPager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabLayout = binding.spriceTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text =
                if (position == 0) SpriceReportListTypeEnum.LAST_24_HOURS.getLocalizedName(requireContext()) else SpriceReportListTypeEnum.ALL.getLocalizedName(
                    requireContext()
                )
        }.attach()

        val fab = binding.spriceFab

        fab.setOnClickListener { view ->
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "New icing report")
                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report list")
                param(FirebaseAnalytics.Param.SCREEN_CLASS, "IcingReportList")
            }

            Navigation.findNavController(view)
                .navigate(R.id.action_sprice_fragment_to_icing_report_fragment)
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val reports = spriceDbRepository.getIcingReports()

            Log.d("SPRICE", "Listed ${reports.count()} reports")
            Log.d("SPRICE", "Found ${spriceDbRepository.getAllImageUris().count()} image URIs")
            for (report in reports) {
                val textView = TextView(requireContext())
                textView.text = report.WebKitFormBoundaryId

                // add TextView to LinearLayout
//                reportsLayout.addView(view)
            }
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUMBER_OF_PAGES

        override fun createFragment(position: Int): Fragment = SpriceIcingReportListFragment.newInstance(
            if (position == 1) SpriceReportListTypeEnum.LAST_24_HOURS else SpriceReportListTypeEnum.ALL,
            spriceDbRepository
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
