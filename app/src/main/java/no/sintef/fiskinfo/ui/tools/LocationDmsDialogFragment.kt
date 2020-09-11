package no.sintef.fiskinfo.ui.tools

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.location_dms_editor_fragment.view.*
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker
import no.sintef.fiskinfo.utilities.ui.IntRangeValidator


class LocationDmsDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDmsViewModel
    private lateinit var mBinding: LocationDmsEditorFragmentBinding


    interface LocationDmsDialogListener {
        fun onDmsEditConfirmed()
    }

    fun createView(
        inflater: LayoutInflater, container: ViewGroup?
    ): View? {
        mBinding = DataBindingUtil.inflate<LocationDmsEditorFragmentBinding>(
            inflater,
            no.sintef.fiskinfo.R.layout.location_dms_editor_fragment,
            container,
            false
        )

        val root = mBinding.root

        root.latitude_degrees_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_degrees_edit_text,
                0,
                true,
                90,
                true,
                { viewModel.dmsLocation.value!!.latitudeDegrees = it.toInt() })
        )

        root.latitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.dmsLocation.value!!.latitudeMinutes = it.toInt() })
        )

        root.latitude_seconds_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_seconds_edit_text,
                0,
                true,
                60,
                false,
                { viewModel.dmsLocation.value!!.latitudeSeconds = it.toDouble() })
        )


        root.longitude_degrees_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_degrees_edit_text,
                0,
                true,
                180,
                true,
                { viewModel.dmsLocation.value!!.longitudeDegrees = it.toInt() })
        )

        root.longitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.dmsLocation.value!!.longitudeMinutes = it.toInt() })
        )

        root.longitude_seconds_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_seconds_edit_text,
                0,
                true,
                60,
                false,
                { viewModel.dmsLocation.value!!.longitudeSeconds = it.toDouble() })
        )

        mBinding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        return root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = createView(activity!!.layoutInflater, null)

        val builder = MaterialAlertDialogBuilder(context)
            .setTitle("Edit position")
//            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
//                dismiss();
//            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                val listener: LocationDmsDialogListener? = targetFragment as LocationDmsDialogListener?
                listener?.onDmsEditConfirmed()
                dismiss()

            }
        builder.setView(view)

        return builder.create()
    }

    fun setLocationToCurrentPosition() {
        viewModel.getLocation();

        var tracker = GpsLocationTracker(requireContext())
        if (tracker.canGetLocation()) {

            var loc = tracker.location
            if (loc != null) {
                viewModel.setNewLocation(loc)
            }

        } else {
            tracker.showSettingsAlert()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(LocationDmsViewModel::class.java)

        viewModel.dmsLocation.observe(this, Observer { dmsLoc ->
            if (dmsLoc != null) {
                mBinding?.viewmodel = dmsLoc
            }
        })
        super.onActivityCreated(savedInstanceState)
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

}