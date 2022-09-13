package no.sintef.fiskinfo.ui.sprice

import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.GridView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.SpriceReportIcingFragmentBinding
import no.sintef.fiskinfo.model.sprice.*
import no.sintef.fiskinfo.repository.OrapRepository
import no.sintef.fiskinfo.ui.layout.TextInputLayoutGridViewAdapter
import no.sintef.fiskinfo.ui.layout.TextInputLayoutGridViewModel
import no.sintef.fiskinfo.ui.tools.*
import no.sintef.fiskinfo.ui.tools.LocationDmsDialogFragment.LocationDmsDialogListener
import no.sintef.fiskinfo.util.DMSLocation
import java.util.*

class ReportIcingFragment : LocationRecyclerViewAdapter.OnLocationInteractionListener, FragmentResultListener,
    Fragment(),
    LocationDmsDialogListener {

    companion object {
        fun newInstance() = ReportIcingFragment()
    }

    private lateinit var mViewModel: ReportIcingViewModel
    private lateinit var mLocationViewModel: LocationViewModel

    private lateinit var seaIcingGridView: GridView
    private lateinit var vesselIcingGridView: GridView
    private lateinit var windObservationsGridView: GridView

    private lateinit var mReportingHourAdapter: DropDownMenuArrayAdapter<IcingReportHourEnum>
    private lateinit var mSynopHourDropdown: AutoCompleteTextView

    private lateinit var locAdapter: LocationRecyclerViewAdapter

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

        initInputAndGridViews()

        return mBinding.root
    }

    private fun initInputAndGridViews() {
        initTimeInputs()
        initSeaIcingGridView()
        initVesselIcingGridView()
        initWindObservationsGridView()
        initPositionInput()
    }

    private fun initPositionInput() {
        locAdapter = LocationRecyclerViewAdapter(this)
        mBinding.icingObservationPositionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBinding.icingObservationPositionRecyclerView.adapter = locAdapter
    }

    private fun initTimeInputs() {
        initReportingHourDropDown()

        mBinding.icingReportDateField.setOnClickListener {
            val builder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
            builder.setSelection(mViewModel.synopDate.value!!.time)
            Log.e("setSelection", "Reporting time: ${mViewModel.synopDate.value!!.time}")

            val picker: MaterialDatePicker<*> = builder.build()
            picker.addOnPositiveButtonClickListener {
                val cal = Calendar.getInstance()
                cal.timeInMillis = it as Long
                mViewModel.setObservationDate(cal.time)
            }
            picker.show(parentFragmentManager, picker.toString())
        }
    }

    private fun initWindObservationsGridView() {
        windObservationsGridView = mBinding.reportIcingWindObservationsGridView
        val windObservationsInputsArrayList: ArrayList<TextInputLayoutGridViewModel<MaxMiddleWindTimeEnum>> = ArrayList()

        windObservationsInputsArrayList.add(TextInputLayoutGridViewModel(
            fieldName = getString(R.string.max_middle_wind_when_hint),
            hint = getString(R.string.max_middle_wind_when_hint),
            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
            inputType = InputType.TYPE_NULL,
            onClickListener = { parent, _, position, _ ->
                mViewModel.maxMiddleWindTime.value = parent.getItemAtPosition(position) as MaxMiddleWindTimeEnum
            },
            dropDownAdapter = DropDownMenuArrayAdapter(
                requireContext(),
                R.layout.exposed_dropdown_menu_item,
                MaxMiddleWindTimeEnum.values().drop(1).toTypedArray()
            ))
        )
        windObservationsGridView.adapter = TextInputLayoutGridViewAdapter(requireContext(), windObservationsInputsArrayList)
    }

    private fun initVesselIcingGridView() {
        val vesselIcingInputsArrayList: ArrayList<TextInputLayoutGridViewModel<IDropDownMenu>> = ArrayList<TextInputLayoutGridViewModel<IDropDownMenu>>()
        vesselIcingGridView = mBinding.reportIcingVesselIcingGridView

        vesselIcingInputsArrayList.add(TextInputLayoutGridViewModel(
            fieldName = getString(R.string.icing_report_vessel_icing_thickness_hint),
            hint = getString(R.string.icing_report_vessel_icing_thickness_hint),
            suffixText = getString(R.string.icing_report_vessel_icing_thickness_suffix)))
        vesselIcingInputsArrayList.add(TextInputLayoutGridViewModel(
            fieldName = getString(R.string.icing_report_vessel_reason_for_icing_hint),
            hint = getString(R.string.icing_report_vessel_reason_for_icing_hint),
            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
            onClickListener = { parent, _, position, _ ->
                mViewModel.reasonForVesselIcing.value = parent.getItemAtPosition(position) as ReasonForIcingOnVesselOrPlatformEnum
            },
            dropDownAdapter = DropDownMenuArrayAdapter(
                requireContext(),
                R.layout.exposed_dropdown_menu_item,
                ReasonForIcingOnVesselOrPlatformEnum.values().drop(1).toTypedArray()
            )))
        vesselIcingInputsArrayList.add(TextInputLayoutGridViewModel(
            fieldName = getString(R.string.icing_report_vessel_change_in_icing),
            hint = getString(R.string.icing_report_vessel_change_in_icing),
            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
            inputType = InputType.TYPE_CLASS_TEXT,
            onClickListener = { parent, _, position, _ ->
                mViewModel.vesselIcingChangeInIcing.value = parent.getItemAtPosition(position) as ChangeInIcingOnVesselOrPlatformEnum
            },
            dropDownAdapter = DropDownMenuArrayAdapter(
                requireContext(),
                R.layout.exposed_dropdown_menu_item,
                ChangeInIcingOnVesselOrPlatformEnum.values().drop(1).toTypedArray()
            )))

        vesselIcingGridView.adapter = TextInputLayoutGridViewAdapter(requireContext(), vesselIcingInputsArrayList)
    }

    private fun initSeaIcingGridView() {
        seaIcingGridView = mBinding.reportIcingSeaIcingGridView
        val seaIcingInputsArrayList: ArrayList<TextInputLayoutGridViewModel<SeaIceConditionsAndDevelopmentEnum>> = ArrayList()

        seaIcingInputsArrayList.add(TextInputLayoutGridViewModel(
            fieldName = getString(R.string.icing_report_sea_ice_conditions_and_development_hint),
            hint = getString(R.string.icing_report_sea_ice_conditions_and_development_hint),
            textAlignment = View.TEXT_ALIGNMENT_VIEW_START,
            onClickListener = { parent, _, position, _ ->
                mViewModel.seaIcingConditionsAndDevelopment.value = parent.getItemAtPosition(position) as SeaIceConditionsAndDevelopmentEnum
            },
            dropDownAdapter = DropDownMenuArrayAdapter(
                requireContext(),
                R.layout.exposed_dropdown_menu_item,
                SeaIceConditionsAndDevelopmentEnum.values().drop(1).toTypedArray()
            ))
        )
        seaIcingGridView.adapter = TextInputLayoutGridViewAdapter(requireContext(), seaIcingInputsArrayList)
    }

    private fun initReportingHourDropDown() {
        mReportingHourAdapter = DropDownMenuArrayAdapter(
            requireContext(),
            R.layout.exposed_dropdown_menu_item,
            IcingReportHourEnum.values()
        )
        mSynopHourDropdown = mBinding.icingReportTimeField

        mSynopHourDropdown.setOnItemClickListener { parent, _, time, _ ->
            mViewModel.synopHourSelect.value = (parent.getItemAtPosition(time) as IcingReportHourEnum).code
            val calendar = Calendar.getInstance()
            calendar.time = mViewModel.synopDate.value!!
            calendar.set(Calendar.HOUR_OF_DAY, mViewModel.getSynopHourAsInt())

            mViewModel.synopDate.value = calendar.time
        }

        mSynopHourDropdown.setAdapter(mReportingHourAdapter)
    }

    override fun onDmsEditConfirmed() {
        val location = mLocationViewModel.getLocation()
        if (location != null) {
            mViewModel.location.value = location
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        mViewModel = ViewModelProvider(requireActivity())[ReportIcingViewModel::class.java]
        mLocationViewModel = ViewModelProvider(requireActivity())[LocationDmsViewModel::class.java]
        mViewModel.init()

        mViewModel.synopDate.observe(viewLifecycleOwner) {
            mBinding.viewmodel = mViewModel
        }

        mViewModel.location.observe(viewLifecycleOwner) {
            locAdapter.locations = listOf<Location>(it)
        }
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
                menuInflater.inflate(R.menu.sprice_report_icing_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // TODO: Remove when submission of report is working
                Log.w("Request", mViewModel.getIcingReportBody().getRequestBodyForReportSubmissionAsString())
                Log.e(
                    "onOptionsItemSelected", "\nSynop date: ${mViewModel.synopDate.value}, synop time: ${mViewModel.synopHourSelect.value}, reporting time: ${mViewModel.reportingTime.value}, synop unix: ${mViewModel.synopDate.value!!.time}\n" +
                            "air temp: ${mViewModel.airTemperature.value}, sea temp: ${mViewModel.seaTemperature.value}, icing thickness: ${mViewModel.vesselIcingThickness.value},\n" +
                            "${mViewModel.maxMiddleWindTime.value.getFormValue()},\n" +
                            "location: (lat: ${mViewModel.location.value?.latitude}, lon: ${mViewModel.location.value?.longitude})"
                )

                if (menuItem.itemId == R.id.send_icing_report_action) {
                    if(!reportedIcingValuesAreValid()) {
                        return true
                    }

                    val requestBody = mViewModel.getIcingReportBody()
                    val repository = OrapRepository.getInstance(requireContext(), requestBody.Username, requestBody.Password, requestBody.WebKitFormBoundaryId)
                    val result = repository.sendIcingReport(requestBody)

                    result.observe(viewLifecycleOwner) {
                        Log.e("ORAP", "Icing reported")
                        if (it.success) {
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                param(FirebaseAnalytics.Param.CONTENT_TYPE, "Send icing report, success")
                                param(FirebaseAnalytics.Param.SCREEN_NAME, "Icing report")
                                param(FirebaseAnalytics.Param.SCREEN_CLASS, "ReportIcingFragment")
                            }

                            val text = getString(R.string.icing_report_sent)
                            val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
                            toast.show()
                            Navigation.findNavController(requireView()).navigateUp()
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

                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun reportedIcingValuesAreValid(): Boolean {
        var valid = true
        if (mViewModel.maxMiddleWindTime.value == MaxMiddleWindTimeEnum.NOT_SELECTED) {
            // TODO: Add error icon
            valid = false
        } else {
            // TODO: remove error icon
        }
        if(mViewModel.seaIcingConditionsAndDevelopment.value == SeaIceConditionsAndDevelopmentEnum.NOT_SELECTED) {
            seaIcingGridView.findViewWithTag<View>("") // TODO: Add error icon
            valid = false
        } else {
            seaIcingGridView.findViewWithTag<View>("") // TODO: remove error icon
        }

        // TODO: Check other values

        Log.e("checkReportedValues", "Values are ${if(!valid) "not" else ""} valid!")
        return valid
    }

    override fun onEditLocationClicked(v: View, itemClicked: Int) {
        mLocationViewModel.initWithLocation(mViewModel.location.value!!, itemClicked)
        val fm: FragmentManager = parentFragmentManager
        val locDialogFragment: LocationDmsDialogFragment =
            LocationDmsDialogFragment.newInstance(getString(R.string.tool_edit_location))

        fm.setFragmentResultListener(DMSLocation.EDIT_DMS_POSITION_FRAGMENT_RESULT_REQUEST_KEY, viewLifecycleOwner, this)
        locDialogFragment.show(fm, "fragment_edit_location")
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        if(DMSLocation.EDIT_DMS_POSITION_FRAGMENT_RESULT_REQUEST_KEY == requestKey) {
            val location = mLocationViewModel.getLocation()
            if (location != null) {
                mViewModel.location.value = location
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }
}