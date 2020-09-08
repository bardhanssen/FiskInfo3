package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.databinding.SnapEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker

class LocationDmmEditorFragment : Fragment() {

    companion object {
        fun newInstance() = LocationDmmEditorFragment()
    }

    private lateinit var viewModel: LocationDmmEditorViewModel
    private var mBinding: LocationDmsEditorFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<LocationDmsEditorFragmentBinding>(
            inflater,
            R.layout.location_dms_editor_fragment,
            container,
            false
        )

        //mBinding = LocationDmsEditorFragmentBinding.inflate(layoutInflater, container, false)
        mBinding!!.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(LocationDmmEditorViewModel::class.java)

  /*      viewModel.dmsLocation.observe(this, Observer { dmsLoc ->
            if (dmsLoc != null) {
                mBinding?.viewmodel = dmsLoc
            }
        })

        //viewModel.latitudeDegrees.observe(this, Observer {mBinding?.latitudeDegreesEditText?.setText(it.toString())})
        //viewModel.latitudeMinutes.observe(this, Observer {mBinding?.latitudeMinutesEditText?.setText(it.toString())})
        //viewModel.latitudeSeconds.observe(this, Observer {mBinding?.latitudeSecondsEditText?.setText(it.toString())})
        viewModel.latitudeSouth.observe(this, Observer {mBinding?.latitudeCardinalDirectionSwitch?.isChecked = it})
        viewModel.longitudeDegrees.observe(this, Observer {mBinding?.longitudeDegreesEditText?.setText(it.toString())})
        viewModel.longitudeMinutes.observe(this, Observer {mBinding?.longitudeMinutesEditText?.setText(it.toString())})
        viewModel.longitudeSeconds.observe(this, Observer {mBinding?.longitudeSecondsEditText?.setText(it.toString())})
        viewModel.longitudeWest.observe(this, Observer {mBinding?.longitudeCardinalDirectionSwitch?.isChecked = it})
*/
/*        mViewModel.locations.observe(this, Observer { locAdapter.locations = it })

        toolTypeCodeName.observe(
            this, Observer {
                mBinding.deploymentviewmodel = mViewModel
            })
*/
    }

    fun setLocationToCurrentPosition() {
        var tracker = GpsLocationTracker(requireContext())
        if (tracker.canGetLocation()) {

            var loc = tracker.location
            if (loc != null) {
                viewModel.initWithLocation(loc)
            }

        } else {
            tracker.showSettingsAlert()
        }
    }




}