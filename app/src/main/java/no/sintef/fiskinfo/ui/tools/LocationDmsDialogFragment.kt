/**
 * Copyright (C) 2020 SINTEF
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

        val view = createView(requireActivity().layoutInflater, null)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.tool_edit_location))
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
        viewModel = ViewModelProviders.of(requireActivity()).get(LocationDmsViewModel::class.java)

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