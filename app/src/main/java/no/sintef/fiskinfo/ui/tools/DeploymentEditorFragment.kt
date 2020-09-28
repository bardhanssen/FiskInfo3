package no.sintef.fiskinfo.ui.tools

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.ToolDeploymentEditorFragmentBinding
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import no.sintef.fiskinfo.ui.tools.LocationDmsDialogFragment.LocationDmsDialogListener
import java.util.*

class DeploymentEditorFragment: LocationRecyclerViewAdapter.OnLocationInteractionListener, Fragment(),
    LocationDmsDialogListener {
    companion object {
        fun newInstance() = DeploymentEditorFragment()
    }

    private lateinit var mViewModel: DeploymentViewModel
    private lateinit var mLocationViewModel : LocationViewModel
    private var _mBinding: ToolDeploymentEditorFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val mBinding get() = _mBinding!!

    private lateinit var mToolCodeAdapter : ToolTypeCodeArrayAdapter
    private lateinit var mEditTextFilledExposedDropdown: AutoCompleteTextView
    private lateinit var locAdapter : LocationRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        _mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.tool_deployment_editor_fragment,
            container,
            false
        )

        mToolCodeAdapter = ToolTypeCodeArrayAdapter(
            context,
            R.layout.exposed_dropdown_menu_item,
            ToolTypeCode.values()
        )
        mEditTextFilledExposedDropdown = mBinding.toolDetailsTypeField
        mEditTextFilledExposedDropdown.setOnItemClickListener { parent, view, position, id -> mViewModel.toolTypeCode.value = parent.getItemAtPosition(
            position
        ) as ToolTypeCode }
        mEditTextFilledExposedDropdown.setAdapter(mToolCodeAdapter)

        mBinding.toolDetailsDateField.setOnClickListener  {
            val builder : MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
            builder.setSelection(mViewModel.setupTime.value!!.time)
            val picker : MaterialDatePicker<*> = builder.build()
            picker.addOnPositiveButtonClickListener {
                var cal = Calendar.getInstance()
                cal.timeInMillis = it as Long
                mViewModel.setSetupDate(cal.time)
            }
            picker.show(parentFragmentManager, picker.toString())
        }

        mBinding.toolDetailsTimeField.setOnClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
        }


        mBinding.toolPositionRecyclerView.layoutManager = LinearLayoutManager(context)
        locAdapter = LocationRecyclerViewAdapter(this)
        mBinding.toolPositionRecyclerView.setAdapter(locAdapter)

        mBinding.addPositionButton.setOnClickListener { mViewModel.addLocation()}
        mBinding.removePositionButton.setOnClickListener { mViewModel.removeLastLocation() }

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(requireActivity()).get(DeploymentViewModel::class.java)
        mViewModel.initContent()

        mLocationViewModel = ViewModelProviders.of(requireActivity()).get(LocationDmsViewModel::class.java)

        // Refresh the full UI when there is a change, as this UI is small
        mViewModel.toolTypeCodeName.observe(
            viewLifecycleOwner, Observer {
                mBinding.deploymentviewmodel = mViewModel
            })

        mViewModel.setupTime.observe(viewLifecycleOwner, Observer {
            mBinding.deploymentviewmodel = mViewModel
        })

        mViewModel.locations.observe(viewLifecycleOwner, Observer { locAdapter.locations = it })

        mViewModel.getFiskInfoProfileDTO()?.observe(viewLifecycleOwner, Observer {
            // Set up observer for fisk info profile so it is ready for when we need it
            // Maybe activate send button when this is done?
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tool_deployment_editor_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.send_tool_report_action) {

            if (mViewModel.canSendReport()) {
                var result = mViewModel.sendReport();
                result.observe(this, Observer {
                    if (it.success) {
                        val text = "Tool deployment sent!"
                        val toast = Toast.makeText(this.requireActivity(), text,  Toast.LENGTH_SHORT)
                        toast.show()
                        mViewModel.clear()
                        Navigation.findNavController(this.requireView()).navigateUp()
                    }
                    else {
                        Snackbar.make(requireView(), "Tool deployment error: " + it.errorMsg, Snackbar.LENGTH_LONG)
                            .show()
                    }
                })
            } else {
                Snackbar.make(requireView(), "Tool report or profile not complete" , Snackbar.LENGTH_LONG)
                    .show()
                // TODO give feedback that it cannot be sent
            }
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onEditLocationClicked(v: View, itemClicked: Int) {
        mLocationViewModel.initWithLocation(mViewModel.locations.value!![itemClicked], itemClicked)
        val fm: FragmentManager? = parentFragmentManager

        val locDialogFragment: LocationDmsDialogFragment =
            LocationDmsDialogFragment.newInstance("Edit location")
        // SETS the target fragment for use later when sending results
        locDialogFragment.setTargetFragment(this@DeploymentEditorFragment, 300)
        locDialogFragment.show(fm!!, "fragment_edit_location")

//        val locDialogFragment: LocationDmsDialogFragment =
//            LocationDmsDialogFragment.newInstance("Edit location")
//        editNameDialogFragment.show(fm, "fragment_edit_location")

//        Navigation.findNavController(v).navigate(R.id.action_deployment_editor_fragment_to_location_editor_fragment)
    }

    override fun onDmsEditConfirmed() {
        val location =  mLocationViewModel.getLocation()
        if (location != null) {
            mViewModel.locations.value!![mLocationViewModel.listPosition] = location!!
            mViewModel.locations.postValue(mViewModel.locations.value)
        }
    }

    class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
        private lateinit var mViewModel: DeploymentViewModel
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            mViewModel = ViewModelProviders.of(requireActivity()).get(DeploymentViewModel::class.java)
            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            c.time = mViewModel.setupTime.value
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
            mViewModel.setSetupTime(hourOfDay, minute);
            // Do something with the time chosen by the user
        }
    }


}