package no.sintef.fiskinfo.ui.tools

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import no.sintef.fiskinfo.databinding.LocationEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker

class LocationEditorFragment : Fragment() {

    companion object {
        fun newInstance() = LocationEditorFragment()
    }

    private lateinit var viewModel: LocationEditorViewModel
    private var mBinding: LocationEditorFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = LocationEditorFragmentBinding.inflate(layoutInflater, container, false)
        mBinding!!.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(LocationEditorViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.latitudeSouth.observe(this, Observer {mBinding?.latitudeCardinalDirectionSwitch?.isChecked = it})
        viewModel.longitudeWest.observe(this, Observer {mBinding?.longitudeCardinalDirectionSwitch?.isChecked = it})
        viewModel.latitudePrimary.observe(this, Observer {mBinding?.latitudeDegreesEditText?.setText(it.toString())})
        viewModel.latitudeSecondary.observe(this, Observer {mBinding?.latitudeMinutesEditText?.setText(it.toString())})
        viewModel.longitudePrimary.observe(this, Observer {mBinding?.longitudeDegreesEditText?.setText(it.toString())})
        viewModel.longitudeSecondary.observe(this, Observer {mBinding?.longitudeMinutesEditText?.setText(it.toString())})

        val showSeconds = viewModel.format == Location.FORMAT_SECONDS
        mBinding?.latitudeSecondsEditText?.visibility = (if (showSeconds) View.VISIBLE else View.GONE)
        mBinding?.longitudeSecondsEditText?.visibility = (if (showSeconds) View.VISIBLE else View.GONE)

        if (showSeconds) {
            viewModel.latitudeTertiary.observe(this, Observer {mBinding?.latitudeSecondsEditText?.setText(it.toString())})
            viewModel.longitudeTertiary.observe(this, Observer {mBinding?.longitudeSecondsEditText?.setText(it.toString())})
        }

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