package no.sintef.fiskinfo.ui.tools

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.location_dms_editor_fragment.view.*
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker


class LocationDmsDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDmsEditorViewModel
    private lateinit var mBinding: LocationDmsEditorFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<LocationDmsEditorFragmentBinding>(
            inflater,
            no.sintef.fiskinfo.R.layout.location_dms_editor_fragment,
            container,
            false
        )

        val root = mBinding.root

        root.latitude_degrees_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.latitude_degrees_edit_text,
                0,
                true,
                90,
                true,
                { viewModel.dmsLocation.value!!.latitudeDegrees = it.toInt() })
        )

        root.latitude_minutes_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.latitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.dmsLocation.value!!.latitudeMinutes = it.toInt() })
        )

        root.latitude_seconds_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.latitude_seconds_edit_text,
                0,
                true,
                60,
                false,
                { viewModel.dmsLocation.value!!.latitudeSeconds = it.toDouble() })
        )


        root.longitude_degrees_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.longitude_degrees_edit_text,
                0,
                true,
                180,
                true,
                { viewModel.dmsLocation.value!!.longitudeDegrees = it.toInt() })
        )

        root.longitude_minutes_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.longitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.dmsLocation.value!!.longitudeMinutes = it.toInt() })
        )

        root.longitude_seconds_edit_text.addTextChangedListener(
            LocationDmsEditorFragment.IntRangeValidator(root.longitude_seconds_edit_text,
                0,
                true,
                60,
                false,
                { viewModel.dmsLocation.value!!.longitudeSeconds = it.toDouble() })
        )

        //mBinding = LocationDmsEditorFragmentBinding.inflate(layoutInflater, container, false)
        mBinding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        return root
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get field from view
       // mEditText = view.findViewById(R.id.txt_your_name)
        // Fetch arguments from bundle and set title
        val title = arguments!!.getString("title", "Enter Name")
        dialog!!.setTitle(title)
        // Show soft keyboard automatically and request focus to field
      // mEditText!!.requestFocus()
      //  dialog!!.window.setSoftInputMode(
      //      WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
      // )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(LocationDmsEditorViewModel::class.java)

        viewModel.dmsLocation.observe(this, Observer { dmsLoc ->
            if (dmsLoc != null) {
                mBinding?.viewmodel = dmsLoc
            }
        })
/*
        viewModel.latitudeSouth.observe(
            this,
            Observer { mBinding?.latitudeCardinalDirectionSwitch?.isChecked = it })

 */
    }

    companion object {
        fun newInstance(title: String?): LocationDmsDialogFragment {
            val frag = LocationDmsDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
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


}