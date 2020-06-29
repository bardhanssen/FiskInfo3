package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.ToolDeploymentEditorFragmentBinding
import no.sintef.fiskinfo.model.fishingfacility.FishingFacility
import no.sintef.fiskinfo.model.fishingfacility.ToolTypeCode
import java.time.Instant
import java.util.*

class DeploymentEditorFragment: LocationRecyclerViewAdapter.OnLocationInteractionListener, Fragment() {
    companion object {
        fun newInstance() = DeploymentEditorFragment()
    }

    private lateinit var mViewModel: DeploymentViewModel
    private lateinit var mLocationViewModel : LocationEditorViewModel
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
        _mBinding = DataBindingUtil.inflate(inflater, R.layout.tool_deployment_editor_fragment, container, false)

        mToolCodeAdapter = ToolTypeCodeArrayAdapter(context, R.layout.exposed_dropdown_menu_item, ToolTypeCode.values())
        mEditTextFilledExposedDropdown = mBinding.toolDetailsTypeField

        mEditTextFilledExposedDropdown.setOnItemClickListener { parent, view, position, id -> mViewModel.toolTypeCode.value = parent.getItemAtPosition(position) as ToolTypeCode }
        mEditTextFilledExposedDropdown.setAdapter(mToolCodeAdapter)

        mBinding.toolDetailsDateLayout.setStartIconOnClickListener  {
            val builder : MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
            //val currentTimeInMillis = Calendar.getInstance().timeInMillis
            //builder.setSelection(currentTimeInMillis)
            val picker : MaterialDatePicker<*> = builder.build()
            picker.addOnPositiveButtonClickListener {
                var cal = Calendar.getInstance()
                cal.timeInMillis = it as Long
                mViewModel.setSetupDate( cal.time )
            }
            picker.show(fragmentManager!!, picker.toString())
        }
        mBinding.toolPositionRecyclerView.layoutManager = LinearLayoutManager(context)
        locAdapter = LocationRecyclerViewAdapter(this)
        mBinding.toolPositionRecyclerView.setAdapter(locAdapter)

        mBinding.addPositionButton.setOnClickListener {
            mViewModel.addLocation()
        }

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(DeploymentViewModel::class.java)
        mViewModel.clearInfo()
        mLocationViewModel = ViewModelProviders.of(activity!!).get(LocationEditorViewModel::class.java)

        // Refresh the full UI when there is a change, as this UI is small
        mViewModel.toolTypeCodeName.observe(
            this, Observer {
            mBinding.deploymentviewmodel = mViewModel
        })

        mViewModel.setupTime.observe(this, Observer {
            mBinding.deploymentviewmodel = mViewModel
        })

        mViewModel.locations.observe(this, Observer { locAdapter.locations = it })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onEditLocationClicked(v: View, location: Location) {
        mLocationViewModel.initWithLocation(location)
        Navigation.findNavController(v).navigate(R.id.action_deployment_editor_fragment_to_location_editor_fragment)
    }

}