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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.util.DMSLocation
import no.sintef.fiskinfo.util.GpsLocationTracker
import no.sintef.fiskinfo.utilities.ui.IntRangeValidator


class LocationDmsDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDmsViewModel
    private var _mBinding: LocationDmsEditorFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = LocationDmsEditorFragmentBinding.inflate(inflater, container, false)

        binding.latitudeDegreesEditText.addTextChangedListener(
            IntRangeValidator(binding.latitudeDegreesEditText,
                0,
                true,
                90,
                true
            ) { viewModel.dmsLocation.value!!.latitudeDegrees = it.toInt() }
        )

        binding.latitudeMinutesEditText.addTextChangedListener(
            IntRangeValidator(binding.latitudeMinutesEditText,
                0,
                true,
                59,
                true
            ) { viewModel.dmsLocation.value!!.latitudeMinutes = it.toInt() }
        )

        binding.latitudeSecondsEditText.addTextChangedListener(
            IntRangeValidator(binding.latitudeSecondsEditText,
                0,
                true,
                60,
                false
            ) { viewModel.dmsLocation.value!!.latitudeSeconds = it }
        )


        binding.longitudeDegreesEditText.addTextChangedListener(
            IntRangeValidator(binding.longitudeDegreesEditText,
                0,
                true,
                180,
                true
            ) { viewModel.dmsLocation.value!!.longitudeDegrees = it.toInt() }
        )

        binding.longitudeMinutesEditText.addTextChangedListener(
            IntRangeValidator(binding.longitudeMinutesEditText,
                0,
                true,
                59,
                true
            ) { viewModel.dmsLocation.value!!.longitudeMinutes = it.toInt() }
        )

        binding.longitudeSecondsEditText.addTextChangedListener(
            IntRangeValidator(binding.longitudeSecondsEditText,
                0,
                true,
                60,
                false
            ) { viewModel.dmsLocation.value!!.longitudeSeconds = it }
        )

        binding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LocationDmsViewModel::class.java]
        viewModel.dmsLocation.observe(viewLifecycleOwner) { dmsLoc ->
            if (dmsLoc != null) {
//                binding.viewmodel.dmsFlow.value = dmsLoc
//                binding.longitudeDegreesEditText.setText(dmsLoc.longitudeDegrees.toString())
//                binding.longitudeDegreesEditText.setText(dmsLoc.longitudeMinutes.toString())
//                binding.longitudeSecondsEditText.setText(dmsLoc.longitudeSeconds.toString())
//                binding.longitudeCardinalDirectionSwitch.isChecked = !dmsLoc.longitudeWest

                Log.e("TAG", "Updated location: ${dmsLoc.latitudeDegrees}, ${dmsLoc.latitudeMinutes}, ${dmsLoc.latitudeSeconds}")
                Log.e("TAG", "Updated location: ${dmsLoc.longitudeDegrees}, ${dmsLoc.longitudeMinutes}, ${dmsLoc.longitudeSeconds}")
            }
        }

        binding.latitudeDegreesInput.editText?.setText("23")
    }

    interface LocationDmsDialogListener {
        fun onDmsEditConfirmed()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = onCreateView(requireActivity().layoutInflater, null, savedInstanceState)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.tool_edit_location))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                val listener: LocationDmsDialogListener? = targetFragment as LocationDmsDialogListener?
                listener?.onDmsEditConfirmed()
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
                val dmsLocation = DMSLocation.fromLocation(loc)
                binding.latitudeDegreesEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.latitudeMinutesEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.latitudeSecondsEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.latitudeCardinalDirectionSwitch.isChecked = !dmsLocation.longitudeWest

                binding.longitudeDegreesEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.longitudeMinutesEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.latitudeSecondsEditText.setText(dmsLocation.longitudeDegrees.toString())
                binding.longitudeCardinalDirectionSwitch.isChecked = dmsLocation.latitudeSouth
            }

        } else {
            tracker.showSettingsAlert()
        }
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