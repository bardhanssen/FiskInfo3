package no.sintef.fiskinfo.ui.sprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.dal.sprice.IcingReportDAO
import no.sintef.fiskinfo.dal.sprice.ImageUriEntryDAO
import no.sintef.fiskinfo.dal.sprice.SpriceDatabase
import no.sintef.fiskinfo.databinding.SpriceFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SpriceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpriceFragment : Fragment() {
    lateinit var database: SpriceDatabase
    private lateinit var reportDao: IcingReportDAO
    private lateinit var imageUriDao: ImageUriEntryDAO

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private var _mBinding: SpriceFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private lateinit var reportLogLinearLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: This should be gotten from the activity so we don't have to create additional db objects
        database = SpriceDatabase.getInstance(requireContext())
        reportDao = database.getIcingReportDAO()
        imageUriDao = database.getImageUriEntryDAO()

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SPRICE", "Loaded SPRICE fragment")
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        _mBinding = SpriceFragmentBinding.inflate(inflater, container, false)

        reportLogLinearLayout = mBinding.spriceFragmentReportListingLayout

        listReports()

        val fab = mBinding.spriceNewReportFab

        fab.setOnClickListener { view ->
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "New icing report")
                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report list")
                param(FirebaseAnalytics.Param.SCREEN_CLASS, "IcingReportList")
            }

            Navigation.findNavController(view)
                .navigate(R.id.action_sprice_fragment_to_icing_report_fragment)
        }

        return mBinding.root
    }

    private fun listReports() {
        lifecycleScope.launch {
            val reports = reportDao.getAll()

            Log.d("SPRICE", "Listed ${reports.count()} reports")
            Log.d("SPRICE", "Found ${imageUriDao.getAll().count()} image URIs")
            for (report in reports) {
                val view = TextView(requireContext())
                view.text = report.WebKitFormBoundaryId

                // add TextView to LinearLayout
                reportLogLinearLayout.addView(view)
            }
        }
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpriceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpriceFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}