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
import kotlinx.android.synthetic.main.location_ddm_editor_fragment.view.*
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDdmEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker
import no.sintef.fiskinfo.utilities.ui.IntRangeValidator


class LocationDdmDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDdmViewModel
    private lateinit var mBinding: LocationDdmEditorFragmentBinding


    interface LocationDdmDialogListener {
        fun onDdmEditConfirmed()
    }

    fun createView(
        inflater: LayoutInflater, container: ViewGroup?
    ): View? {
        mBinding = DataBindingUtil.inflate<LocationDdmEditorFragmentBinding>(
            inflater,
            no.sintef.fiskinfo.R.layout.location_ddm_editor_fragment,
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
                { viewModel.ddmLocation.value!!.latitudeDegrees = it.toInt() })
        )

        root.latitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.latitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.ddmLocation.value!!.latitudeDecimalMinutes = it.toDouble() })
        )

        root.longitude_degrees_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_degrees_edit_text,
                0,
                true,
                180,
                true,
                { viewModel.ddmLocation.value!!.longitudeDegrees = it.toInt() })
        )

        root.longitude_minutes_edit_text.addTextChangedListener(
            IntRangeValidator(root.longitude_minutes_edit_text,
                0,
                true,
                59,
                true,
                { viewModel.ddmLocation.value!!.longitudeDecimalMinutes = it.toDouble() })
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
                val listener: LocationDdmDialogListener? = targetFragment as LocationDdmDialogListener?
                listener?.onDdmEditConfirmed()
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
        viewModel = ViewModelProviders.of(activity!!).get(LocationDdmViewModel::class.java)

        viewModel.ddmLocation.observe(this, Observer { dmsLoc ->
            if (dmsLoc != null) {
                mBinding?.viewmodel = dmsLoc
            }
        })
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance(title: String?): LocationDdmDialogFragment {
            val frag = LocationDdmDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }

}