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
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDdmEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker
import no.sintef.fiskinfo.utilities.ui.IntRangeValidator


class LocationDdmDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDdmViewModel

    private var _mBinding: LocationDdmEditorFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = LocationDdmEditorFragmentBinding.inflate(inflater, container, false)
        binding.latitudeDegreesEditText.addTextChangedListener(
            IntRangeValidator(binding.latitudeDegreesEditText,
                0,
                true,
                90,
                true
            ) { viewModel.ddmLocation.value!!.latitudeDegrees = it.toInt() }
        )

        binding.latitudeMinutesEditText.addTextChangedListener(
            IntRangeValidator(binding.latitudeMinutesEditText,
                0,
                true,
                59,
                true
            ) { viewModel.ddmLocation.value!!.latitudeDecimalMinutes = it }
        )

        binding.longitudeDegreesEditText.addTextChangedListener(
            IntRangeValidator(binding.longitudeDegreesEditText,
                0,
                true,
                180,
                true
            ) { viewModel.ddmLocation.value!!.longitudeDegrees = it.toInt() }
        )

        binding.longitudeMinutesEditText.addTextChangedListener(
            IntRangeValidator(binding.longitudeMinutesEditText,
                0,
                true,
                59,
                true
            ) { viewModel.ddmLocation.value!!.longitudeDecimalMinutes = it }
        )

        binding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }

        viewModel = ViewModelProvider(requireActivity()).get(LocationDdmViewModel::class.java)

        viewModel.ddmLocation.observe(this) { dmsLoc ->
            if (dmsLoc != null) {
                _mBinding?.viewmodel = dmsLoc
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    interface LocationDdmDialogListener {
        fun onDdmEditConfirmed()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = onCreateView(requireActivity().layoutInflater, null, savedInstanceState)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.tool_edit_location))
//            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
//                dismiss();
//            }
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                val listener: LocationDdmDialogListener? = targetFragment as LocationDdmDialogListener?
                listener?.onDdmEditConfirmed()
                dismiss()

            }
        builder.setView(view)

        return builder.create()
    }

    fun setLocationToCurrentPosition() {
        viewModel.getLocation()

        val tracker = GpsLocationTracker(requireContext())
        if (tracker.canGetLocation()) {

            val loc = tracker.location
            if (loc != null) {
                viewModel.setNewLocation(loc)
            }

        } else {
            tracker.showSettingsAlert()
        }
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