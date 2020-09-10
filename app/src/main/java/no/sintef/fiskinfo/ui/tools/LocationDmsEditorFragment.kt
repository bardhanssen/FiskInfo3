package no.sintef.fiskinfo.ui.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.location_dms_editor_fragment.view.*
import kotlinx.coroutines.withTimeoutOrNull
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker


class LocationDmsEditorFragment : Fragment() {

    companion object {
        fun newInstance() = LocationDmsEditorFragment()
    }

    private lateinit var viewModel: LocationDmsEditorViewModel
    private lateinit var mBinding: LocationDmsEditorFragmentBinding

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

        val root = mBinding.root

        root.latitude_degrees_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_degrees_edit_text, 0, true, 90, true,
                { viewModel.dmsLocation.value!!.latitudeDegrees = it.toInt() })
        )

        root.latitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_minutes_edit_text, 0, true, 59, true,
                { viewModel.dmsLocation.value!!.latitudeMinutes = it.toInt() })
        )

        root.latitude_seconds_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_seconds_edit_text, 0, true, 60, false,
                { viewModel.dmsLocation.value!!.latitudeSeconds = it.toDouble() })
        )


        root.longitude_degrees_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_degrees_edit_text, 0, true, 180, true,
                { viewModel.dmsLocation.value!!.longitudeDegrees = it.toInt() })
        )

        root.longitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_minutes_edit_text, 0, true, 59, true,
                { viewModel.dmsLocation.value!!.longitudeMinutes = it.toInt() })
        )

        root.longitude_seconds_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_seconds_edit_text, 0, true, 60, false,
                { viewModel.dmsLocation.value!!.longitudeSeconds = it.toDouble() })
        )

        //mBinding = LocationDmsEditorFragmentBinding.inflate(layoutInflater, container, false)
        mBinding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        return root
    }

    abstract class TextValidator(private val textView: TextView) : TextWatcher {
        abstract fun validate(textView: TextView?, text: String?):Boolean
        override fun afterTextChanged(s: Editable) {
            val text = textView.text.toString()
            validate(textView, text)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int)
        { /* Don't care */
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
        { /* Don't care */
        }
    }

    class IntRangeValidator(
        private val textView: TextView,
        private val low: Int,
        private val includeLow : Boolean,
        private val high: Int,
        private val includeHigh : Boolean,
        private val updateAction : (Double) -> (Unit)
    ) : TextValidator(textView) {
        override fun validate(textView: TextView?, text: String?):Boolean {
            var num = 0.0;

            try {
                // TODO: Consider locale adapted parsing
                num = text.toString().toDouble()
                val withinLow = if (includeLow) num >= low else num > low
                val withinHigh = if (includeHigh) num <= high else num < high

                if (! (withinHigh && withinLow))
                    textView?.error = "Value must be in range $low to $high"
                else {
                    textView?.error = null
                    if (updateAction != null)
                        updateAction(num)
                }
            } catch (nfe: NumberFormatException) {
                textView?.error = "Value must be in range $low to $high"
                //println("Could not parse $nfe")
            }
            return (textView?.error == null)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(LocationDmsEditorViewModel::class.java)

        viewModel.dmsLocation.observe(this, Observer { dmsLoc ->
            if (dmsLoc != null) {
                mBinding?.viewmodel = dmsLoc
            }
        })

        //viewModel.latitudeDegrees.observe(this, Observer {mBinding?.latitudeDegreesEditText?.setText(it.toString())})
        //viewModel.latitudeMinutes.observe(this, Observer {mBinding?.latitudeMinutesEditText?.setText(it.toString())})
        //viewModel.latitudeSeconds.observe(this, Observer {mBinding?.latitudeSecondsEditText?.setText(it.toString())})
        viewModel.latitudeSouth.observe(
            this,
            Observer { mBinding?.latitudeCardinalDirectionSwitch?.isChecked = it })
/*        viewModel.longitudeDegrees.observe(this, Observer {
            mBinding?.longitudeDegreesEditText?.setText(
                it.toString()
            )
        })
        viewModel.longitudeMinutes.observe(this, Observer {
            mBinding?.longitudeMinutesEditText?.setText(
                it.toString()
            )
        })
        viewModel.longitudeSeconds.observe(this, Observer {
            mBinding?.longitudeSecondsEditText?.setText(
                it.toString()
            )
        })*/
/*        viewModel.longitudeWest.observe(
            this,
            Observer { mBinding?.longitudeCardinalDirectionSwitch?.isChecked = it })*/

/*        mViewModel.locations.observe(this, Observer { locAdapter.locations = it })

        toolTypeCodeName.observe(
            this, Observer {
                mBinding.deploymentviewmodel = mViewModel
            })
*/
    }

    fun setLocationToCurrentPosition() {
        viewModel.getLocation();

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