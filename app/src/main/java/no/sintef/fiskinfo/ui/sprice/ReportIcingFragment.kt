package no.sintef.fiskinfo.ui.sprice

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.FragmentReportIcingBinding
import no.sintef.fiskinfo.model.orap.IcingTypeCode
import no.sintef.fiskinfo.model.orap.MaxMiddleWindTimeEnum
import no.sintef.fiskinfo.model.orap.ReportIcingRequestBody
import no.sintef.fiskinfo.ui.tools.*
import java.time.LocalDateTime
import java.util.*

class ReportIcingFragment : LocationRecyclerViewAdapter.OnLocationInteractionListener,
    Fragment(),
    LocationDmsDialogFragment.LocationDmsDialogListener {

    companion object {
        fun newInstance() = ReportIcingFragment()
    }

    private lateinit var mViewModel: ReportIcingViewModel
    private lateinit var mLocationViewModel: LocationViewModel

    private lateinit var mMaxMiddleWindAdapter: maxMiddleWindTimeArrayAdapter
    private lateinit var mEditTextFilledExposedDropdown: AutoCompleteTextView
    private var _mBinding: FragmentReportIcingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private lateinit var locAdapter: LocationRecyclerViewAdapter
    private lateinit var viewModel: ReportIcingViewModel

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        setHasOptionsMenu(true)
        _mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_report_icing,
            container,
            false
        )

        mMaxMiddleWindAdapter = maxMiddleWindTimeArrayAdapter(
            requireContext(),
            R.layout.exposed_dropdown_menu_item,
            MaxMiddleWindTimeEnum.values()
        )
        mEditTextFilledExposedDropdown = mBinding.icingDetailsTypeField

        mEditTextFilledExposedDropdown.setOnItemClickListener { parent, view, position, id ->
            mViewModel.maxMiddleWindTime.value = parent.getItemAtPosition(
                position
            ) as MaxMiddleWindTimeEnum
        }
        mEditTextFilledExposedDropdown.setAdapter(mMaxMiddleWindAdapter)

        mBinding.icingReportDateField.setOnClickListener {
            val builder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
            builder.setSelection(mViewModel.reportingTime.value!!.time)
            val picker: MaterialDatePicker<*> = builder.build()
            picker.addOnPositiveButtonClickListener {
                var cal = Calendar.getInstance()
                cal.timeInMillis = it as Long
                mViewModel.setReportingDate(cal.time)
            }
            picker.show(parentFragmentManager, picker.toString())
        }

        mBinding.icingReportTimeField.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
        }

        mBinding.icingReportPositionRecyclerView.layoutManager = LinearLayoutManager(context)
        locAdapter = LocationRecyclerViewAdapter(this)
        mBinding.icingReportPositionRecyclerView.setAdapter(locAdapter)

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ReportIcingViewModel::class.java)
        mViewModel = ViewModelProviders.of(requireActivity()).get(ReportIcingViewModel::class.java)
        // TODO: Use the ViewModel
        mViewModel.initContent()

        mLocationViewModel =
            ViewModelProviders.of(requireActivity()).get(LocationDmsViewModel::class.java)

        // Refresh the full UI when there is a change, as this UI is small
        mViewModel.maxMiddleWindTime.observe(
            viewLifecycleOwner, Observer {
                mBinding.reporticingviewmodel = mViewModel
            })

        mViewModel.reportingTime.observe(
            viewLifecycleOwner, Observer {
            mBinding.reporticingviewmodel = mViewModel
        })

        mViewModel.location.observe(viewLifecycleOwner, Observer { locAdapter.locations = it })

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val orapUsername = prefs.getString(getString(R.string.pref_sprice_username_key), "") ?: ""
        val orapPassword = prefs.getString(getString(R.string.pref_sprice_password_key), "") ?: ""

        val report = ReportIcingRequestBody.Builder()
            .Id(0)
            .ObservationTime(LocalDateTime.now())
            .Synop(LocalDateTime.now().minusHours(2).withMinute(0).withSecond(0).withNano(0))
            .Username(orapUsername)
            .Password(orapPassword)
            .CallSign("abc123")
            .Latitude("68.12")
            .Longitude("18.12")
            .IceThicknessInCm("5")
            .build()

        Log.d("TAG", report.GetRequestBodyForReportSubmission())
    }

    override fun onDmsEditConfirmed() {
        val location = mLocationViewModel.getLocation()
        if (location != null) {
            mViewModel.location.value!![mLocationViewModel.listPosition] = location!!
            mViewModel.location.postValue(mViewModel.location.value)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sprice_report_icing_menu, menu)

        menu.getItem(R.id.check_icing_report_action).setVisible(true)
        menu.getItem(R.id.send_icing_report_action).setVisible(false)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.send_icing_report_action) {
            val result = mViewModel.sendIcingReport();

            result.observe(this, Observer {
                if (it.success) {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, success")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    val text = getString(R.string.tool_deployment_sent)
                    val toast = Toast.makeText(this.requireActivity(), text, Toast.LENGTH_SHORT)
                    toast.show()
                    mViewModel.clear()
                    Navigation.findNavController(this.requireView()).navigateUp()
                } else {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, error")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    Snackbar.make(
                        requireView(),
                        getString(R.string.tool_deployment_error) + it.errorMsg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            })

            return true
        }
        else if(item.itemId == R.id.check_icing_report_action) {
            val result = mViewModel.checkReportValues();
            result.observe(this, Observer {
                if (it.success) {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, success")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    val text = getString(R.string.tool_deployment_sent)
                    val toast = Toast.makeText(this.requireActivity(), text, Toast.LENGTH_SHORT)
                    toast.show()
                    mViewModel.clear()
                    Navigation.findNavController(this.requireView()).navigateUp()
                } else {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, error")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Report icing")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    Snackbar.make(
                        requireView(),
                        getString(R.string.tool_deployment_error) + it.errorMsg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            })
            return true
        }
        return false
    }

    override fun onEditLocationClicked(v: View, itemClicked: Int) {
        mLocationViewModel.initWithLocation(mViewModel.location.value!![itemClicked], itemClicked)
        val fm: FragmentManager? = parentFragmentManager

        val locDialogFragment: LocationDmsDialogFragment =
            LocationDmsDialogFragment.newInstance(getString(R.string.tool_edit_location))
        // SETS the target fragment for use later when sending results
        locDialogFragment.setTargetFragment(this@ReportIcingFragment, 300)
        locDialogFragment.show(fm!!, "fragment_edit_location")

//        val locDialogFragment: LocationDmsDialogFragment =
//            LocationDmsDialogFragment.newInstance("Edit location")
//        editNameDialogFragment.show(fm, "fragment_edit_location")

//        Navigation.findNavController(v).navigate(R.id.action_deployment_editor_fragment_to_location_editor_fragment)
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        private lateinit var mViewModel: ReportIcingViewModel
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            mViewModel =
                ViewModelProviders.of(requireActivity()).get(ReportIcingViewModel::class.java)


            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            c.time = mViewModel.reportingTime.value
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(
                activity,
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(activity)
            )
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            mViewModel.setReportingTime(hourOfDay, minute);
            // Do something with the time chosen by the user
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

}