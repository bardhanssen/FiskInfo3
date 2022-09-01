package no.sintef.fiskinfo.ui.sprice

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.SpriceReportIcingFragmentBinding
import no.sintef.fiskinfo.model.sprice.IcingReportHourEnum
import no.sintef.fiskinfo.model.sprice.MaxMiddleWindTimeEnum
import no.sintef.fiskinfo.repository.OrapRepository
import no.sintef.fiskinfo.ui.tools.LocationDmsDialogFragment
import no.sintef.fiskinfo.ui.tools.LocationDmsViewModel
import no.sintef.fiskinfo.ui.tools.LocationRecyclerViewAdapter
import no.sintef.fiskinfo.ui.tools.LocationViewModel
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
    private lateinit var mReportingHourAdapter: DropDownMenuArrayAdapter<IcingReportHourEnum>
    private lateinit var icingDetailsDropdown: AutoCompleteTextView
    private lateinit var mSynopHourDropdown: AutoCompleteTextView
    private var _mBinding: SpriceReportIcingFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        _mBinding = SpriceReportIcingFragmentBinding.inflate(inflater, container, false)

        initMaxMiddleWindDropDown()
        initReportingHourDropDown()
        setHasOptionsMenu(true)
//        initMenu() // TODO: Change setHasOptionsMenu to this

        mBinding.icingReportDateField.setOnClickListener {
            val builder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
            builder.setSelection(mViewModel.reportingTime.value.time)
            val picker: MaterialDatePicker<*> = builder.build()
            picker.addOnPositiveButtonClickListener {
                val cal = Calendar.getInstance()
                cal.timeInMillis = it as Long
                mViewModel.setReportingDate(cal.time)
            }
            picker.show(parentFragmentManager, picker.toString())
        }

        return mBinding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sprice_report_icing_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.w("Request", mViewModel.getIcingReportBody().getRequestBodyForReportSubmissionAsString())

//        return true

        if (item.itemId == R.id.send_icing_report_action) {
            val result = OrapRepository.getInstance(requireContext()).SendIcingReport(mViewModel.getIcingReportBody())

            result.observe(this) {
                if (it.success) {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, success")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    val text = getString(R.string.icing_report_sent)
                    val toast = Toast.makeText(this.requireActivity(), text, Toast.LENGTH_SHORT)
                    toast.show()
//                    mViewModel.clear()
                    Navigation.findNavController(this.requireView()).navigateUp()
                } else {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, error")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    Snackbar.make(
                        requireView(),
                        getString(R.string.icing_report_submit_error) + it.errorMsg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }

            return true
        }
        else if(item.itemId == R.id.check_icing_report_action) {
            checkReportedValues()

//                    .observationTime(LocalDateTime.now())
//                    .synop(LocalDateTime.now().minusHours(2).withMinute(0).withSecond(0).withNano(0))
//                    .username(orapUsername)
//                    .password(orapPassword)
//                    .callSign("abc123")
//                    .latitude("68.12")
//                    .longitude("18.12")
//                    .iceThicknessInCm(5)
//                    .build()
            val report = mViewModel.getIcingReportBody()

            Log.i("TAG", report.getRequestBodyForReportSubmissionAsString())

            val result = OrapRepository.getInstance(requireContext()).checkIcingReportValues(report)

            result.observe(this) {
                if (it.success) {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, success")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    val text = getString(R.string.icing_report_sent)
                    val toast = Toast.makeText(this.requireActivity(), text, Toast.LENGTH_SHORT)
                    toast.show()

                    mViewModel.reportChecked.value = true
//                    mViewModel.clear()

                    // TODO: Switch menu from check to send
                } else {
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, error")
                        param(FirebaseAnalytics.Param.SCREEN_NAME, "Report icing")
                        param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                    }

                    Snackbar.make(
                        requireView(),
                        getString(R.string.icing_report_check_error) + it.errorMsg,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
            return true
        }
        return false
    }

    private fun initMaxMiddleWindDropDown() {
        mMaxMiddleWindAdapter = maxMiddleWindTimeArrayAdapter(
            requireContext(),
            R.layout.exposed_dropdown_menu_item,
            MaxMiddleWindTimeEnum.values()
        )
        icingDetailsDropdown = mBinding.icingDetailsTypeField

        icingDetailsDropdown.setOnItemClickListener { parent, _, position, _ ->
            mViewModel.maxMiddleWindTime.value = parent.getItemAtPosition(position) as MaxMiddleWindTimeEnum
        }
        icingDetailsDropdown.setAdapter(mMaxMiddleWindAdapter)
    }

    private fun initReportingHourDropDown() {
        mReportingHourAdapter = DropDownMenuArrayAdapter(
            requireContext(),
            R.layout.exposed_dropdown_menu_item,
            IcingReportHourEnum.values()
        )
        mSynopHourDropdown = mBinding.icingReportTimeField

        mSynopHourDropdown.setOnItemClickListener { parent, _, position, _ ->
            mViewModel.synopTimeSelect.value = (parent.getItemAtPosition(position) as IcingReportHourEnum).code
        }

        mSynopHourDropdown.setAdapter(mReportingHourAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity())[ReportIcingViewModel::class.java]
        mLocationViewModel = ViewModelProvider(requireActivity())[LocationDmsViewModel::class.java]
//        mLocationViewModel = ViewModelProvider(this)[LocationDmsViewModel::class.java]

//        mViewModel.init()
//        initMenu()

        // Refresh the full UI when there is a change, as this UI is small
//        mViewModel.maxMiddleWindTime.observe(viewLifecycleOwner) { mBinding.reporticingviewmodel = mViewModel }
//        mViewModel.reportingTime.observe(viewLifecycleOwner) { mBinding.reporticingviewmodel = mViewModel }
        mViewModel.location.observe(viewLifecycleOwner) { mBinding.reporticingviewmodel = mViewModel }
        mViewModel.init()
    }

    private fun initMenu() {
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.sprice_report_icing_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                Log.i("Menu Sel", "Selected menu option:")

                if (menuItem.itemId == R.id.send_icing_report_action) {
                    val result = OrapRepository.getInstance(requireContext()).SendIcingReport(mViewModel.getIcingReportBody())

                    result.observe(viewLifecycleOwner) {
                        if (it.success) {
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, success")
                                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                                param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                            }

                            val text = getString(R.string.icing_report_sent)
                            val toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)
                            toast.show()
//                    mViewModel.clear()
                            Navigation.findNavController(mBinding.root).navigateUp()
                        } else {
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, error")
                                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                                param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                            }

                            Snackbar.make(
                                requireView(),
                                getString(R.string.icing_report_submit_error) + it.errorMsg,
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                    }

                    return true
                } else if (menuItem.itemId == R.id.check_icing_report_action) {
                    if (!checkReportedValues()) {
                        return false
                    }

                    /*report =
                            ReportIcingRequestBody.Builder()
                            .observationTime(LocalDateTime.now())
                            .synop(LocalDateTime.now().minusHours(2).withMinute(0).withSecond(0).withNano(0))
                            .callSign("abc123")
                            .latitude("68.12")
                            .longitude("18.12")
                            .iceThicknessInCm(5)
                            .build()*/

                    val report = mViewModel.getIcingReportBody()

                    Log.d("TAG", report.getRequestBodyForReportSubmissionAsString())

                    val result = OrapRepository.getInstance(requireContext()).checkIcingReportValues(report)

                    result.observe(viewLifecycleOwner) {
                        if (it.success) {
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, success")
                                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                                param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                            }

                            val text = getString(R.string.icing_report_sent)
                            val toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)
                            toast.show()

                            mViewModel.reportChecked.value = true
//                    mViewModel.clear()

                            // TODO: Switch menu from check to send
                        } else {
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                param(FirebaseAnalytics.Param.CONTENT_TYPE, "Check icing report, error")
                                param(FirebaseAnalytics.Param.SCREEN_NAME, "Report icing")
                                param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                            }

                            Snackbar.make(
                                requireView(),
                                getString(R.string.icing_report_check_error) + it.errorMsg,
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    return true
                }

                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDmsEditConfirmed() {
        val location = mLocationViewModel.getLocation()
        if (location != null) {
//            mViewModel.location.value!![mLocationViewModel.listPosition] = location!!
            mViewModel.location.postValue(mViewModel.location.value)
        }
    }

    private fun checkReportedValues(): Boolean {
        var invalid = false
        if (mViewModel.maxMiddleWindTime.value != null) {
            mBinding.icingDetailsTypeField.error = getString(R.string.icing_missing_value)
            invalid = true
        }

        // TODO: Check other values

        return invalid
    }

    override fun onEditLocationClicked(v: View, itemClicked: Int) {
//        mLocationViewModel.initWithLocation(mViewModel.location.value!![itemClicked], itemClicked)
        val fm: FragmentManager = parentFragmentManager

        val locDialogFragment: LocationDmsDialogFragment =
            LocationDmsDialogFragment.newInstance(getString(R.string.tool_edit_location))
        // SETS the target fragment for use later when sending results
//        locDialogFragment.setTargetFragment(this@ReportIcingFragment, 300)

        locDialogFragment.setFragmentResultListener(getString(R.string.tool_edit_location)) { _, _ -> run {} }

        locDialogFragment.show(fm, "fragment_edit_location")

        Log.d("editLoc", "Clicked editLocation")

//        val locDialogFragment: LocationDmsDialogFragment =
//            LocationDmsDialogFragment.newInstance("Edit location")
//        editNameDialogFragment.show(fm, "fragment_edit_location")

//        Navigation.findNavController(v).navigate(R.id.action_deployment_editor_fragment_to_location_editor_fragment)
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        private lateinit var mViewModel: ReportIcingViewModel
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            mViewModel =
                ViewModelProvider(requireActivity())[ReportIcingViewModel::class.java]


            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            c.time = mViewModel.reportingTime.value!!
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
            mViewModel.setReportingTime(hourOfDay, minute)
            // Do something with the time chosen by the user
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

}