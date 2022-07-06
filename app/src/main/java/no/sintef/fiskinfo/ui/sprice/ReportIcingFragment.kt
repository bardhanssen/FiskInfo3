package no.sintef.fiskinfo.ui.sprice

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.lifecycle.Observer
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.FragmentReportIcingBinding
import no.sintef.fiskinfo.model.sprice.IcingTypeCode
import no.sintef.fiskinfo.ui.tools.*
import java.util.*

class ReportIcingFragment : LocationRecyclerViewAdapter.OnLocationInteractionListener,
    Fragment(),
    LocationDmsDialogFragment.LocationDmsDialogListener {

    companion object {
        fun newInstance() = ReportIcingFragment()
    }

    private lateinit var mViewModel: ReportIcingViewModel
    private lateinit var mLocationViewModel: LocationViewModel

    private lateinit var mIceCodeAdapter: IcingTypeCodeArrayAdapter
    private lateinit var mEditTextFilledExposedDropdown: AutoCompleteTextView
    private var _mBinding: FragmentReportIcingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private lateinit var locAdapter: LocationRecyclerViewAdapter
    private lateinit var viewModel: ReportIcingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        _mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_report_icing,
            container,
            false
        )

        mIceCodeAdapter = IcingTypeCodeArrayAdapter(
            requireContext(),
            R.layout.exposed_dropdown_menu_item,
            IcingTypeCode.values()
        )
        mEditTextFilledExposedDropdown = mBinding.icingDetailsTypeField

        mEditTextFilledExposedDropdown.setOnItemClickListener { parent, view, position, id ->
            mViewModel.icingTypeCode.value = parent.getItemAtPosition(
                position
            ) as IcingTypeCode
        }
        mEditTextFilledExposedDropdown.setAdapter(mIceCodeAdapter)

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
        mViewModel.icingTypeCodeName.observe(
            viewLifecycleOwner, Observer {
                mBinding.reporticingviewmodel = mViewModel
            })

        mViewModel.windTypeCodeName.observe(
            viewLifecycleOwner, Observer {
                mBinding.reporticingviewmodel = mViewModel
            })

        mViewModel.reportingTime.observe(
            viewLifecycleOwner, Observer {
            mBinding.reporticingviewmodel = mViewModel
        })

        mViewModel.locations.observe(viewLifecycleOwner, Observer { locAdapter.locations = it })
    }

    override fun onDmsEditConfirmed() {
        val location = mLocationViewModel.getLocation()
        if (location != null) {
            mViewModel.locations.value!![mLocationViewModel.listPosition] = location!!
            mViewModel.locations.postValue(mViewModel.locations.value)
        }
    }

    override fun onEditLocationClicked(v: View, itemClicked: Int) {
        mLocationViewModel.initWithLocation(mViewModel.locations.value!![itemClicked], itemClicked)
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

}