package no.sintef.fiskinfo.ui.sprice

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.dal.sprice.SpriceDbRepository
import no.sintef.fiskinfo.databinding.SpriceIcingReportListFragmentBinding
import no.sintef.fiskinfo.model.sprice.ReportIcingRequestPayload
import no.sintef.fiskinfo.model.sprice.SpriceConstants
import no.sintef.fiskinfo.model.sprice.enums.ChangeInIcingOnVesselOrPlatformEnum
import no.sintef.fiskinfo.model.sprice.enums.SpriceReportListTypeEnum
import javax.inject.Inject

class SpriceIcingReportListFragment(repository: SpriceDbRepository) : Fragment(), SpriceReportRecyclerViewAdapter.OnReportInteractionListener {
    @Inject
    lateinit var spriceDbRepository: SpriceDbRepository

    private val mViewModel: SpriceViewModel by activityViewModels()

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private var _mBinding: SpriceIcingReportListFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private var mAdapter: SpriceReportRecyclerViewAdapter? = null
    private var mSwipeLayout: SwipeRefreshLayout? = null
    private lateinit var listType: SpriceReportListTypeEnum
    private lateinit var reportsLayout: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SPRICE", "Loaded SPRICE fragment")
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        _mBinding = SpriceIcingReportListFragmentBinding.inflate(inflater, container, false)
        reportsLayout = mBinding.spriceFragmentReportListingLayout

        if (savedInstanceState != null && savedInstanceState.containsKey(SpriceConstants.SPRICE_REPORT_LIST_TYPE_BUNDLE_KEY)) {
            @Suppress("DEPRECATION")
            listType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                savedInstanceState.getSerializable(SpriceConstants.SPRICE_REPORT_LIST_TYPE_BUNDLE_KEY, SpriceReportListTypeEnum::class.java)!! else
                savedInstanceState.getSerializable(SpriceConstants.SPRICE_REPORT_LIST_TYPE_BUNDLE_KEY)!! as SpriceReportListTypeEnum
        } else {
            listType = SpriceReportListTypeEnum.ALL
        }

        listReports()

        return mBinding.root
    }

    private fun listReports() {
        reportsLayout.layoutManager = LinearLayoutManager(mBinding.root.context)
        mAdapter = SpriceReportRecyclerViewAdapter(this)
        reportsLayout.adapter = mAdapter

        mSwipeLayout = mBinding.icingReportListSwipeLayout
        mSwipeLayout!!.setOnRefreshListener { mViewModel.refreshReports() }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var reports = mViewModel.reports.value
        reports = reports.union(
            listOf(
                ReportIcingRequestPayload.Builder(WebKitFormBoundaryId = "abc", Latitude = "78.0", Longitude = "19.2", ChangeInIce = ChangeInIcingOnVesselOrPlatformEnum.ICE_THAT_DOESNT_BUILD_UP)
                    .build(),
                ReportIcingRequestPayload.Builder(WebKitFormBoundaryId = "defg", Latitude = "74.0", Longitude = "12.2", ChangeInIce = ChangeInIcingOnVesselOrPlatformEnum.ICE_THAT_BUILDS_UP_SLOWLY)
                    .build()
            )
        ).toList()

//        mViewModel.reports.value = reports


//
//        lifecycleScope.launchWhenCreated {  }
//
//        mViewModel.reports.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//            .onEach { (it) ->
//                run {
//                    mAdapter!!.setReports(reports)
//                }
//
//            }
    }

    override fun onDestroyView() {
        Log.d("SPRICE", "Destroyed SPRICE fragment")
        super.onDestroyView()
        _mBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param listType Parameter 1.
         * @return A new instance of fragment SpriceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(listType: SpriceReportListTypeEnum, repository: SpriceDbRepository) =
            SpriceIcingReportListFragment(repository).apply {
                arguments = Bundle().apply {
                    putSerializable(SpriceConstants.SPRICE_REPORT_LIST_TYPE_BUNDLE_KEY, listType)

                }
            }
    }

    override fun onReportViewClicked(v: View, report: ReportIcingViewModel) {
        //TODO: Add report to bundle
        val bundle = Bundle()

//        bundle.putParcelable("", report)
        //        mViewModel.selectTool(report)

        Navigation.findNavController(v).navigate(R.id.action_sprice_fragment_to_icing_report_fragment)
    }

    override fun onSendReportClicked(v: View, report: ReportIcingViewModel) {
        // TODO: Implement sending
    }

    override fun onDeleteReportClicked(v: View, report: ReportIcingViewModel) {
        Toast.makeText(this.requireActivity(), report.reportingTime.value.toString(), Toast.LENGTH_SHORT).show()
    }
}