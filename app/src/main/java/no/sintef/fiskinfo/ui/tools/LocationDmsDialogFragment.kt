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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.LocationDmsEditorFragmentBinding
import no.sintef.fiskinfo.util.GpsLocationTracker


class LocationDmsDialogFragment : DialogFragment() {

    private lateinit var viewModel: LocationDmsViewModel
    private var _mBinding: LocationDmsEditorFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = LocationDmsEditorFragmentBinding.inflate(inflater, container, false)

        binding.apply {
            this.lifecycleOwner = this@LocationDmsDialogFragment
            this.viewmodel = viewmodel
        }

        binding.setToCurrentPositionIcon.setOnClickListener { setLocationToCurrentPosition() }
        viewModel = ViewModelProvider(requireActivity())[LocationDmsViewModel::class.java]

        Log.e("onCreateView", "view model location: ${viewModel.getLocation().latitude}, ${viewModel.getLocation().longitude}")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("onViewCreated", "view model location: ${viewModel.getLocation().latitude}, ${viewModel.getLocation().longitude}")

//        _mBinding = LocationDmsEditorFragmentBinding.bind(view)

        binding.apply {
            this.lifecycleOwner = this@LocationDmsDialogFragment
            this.viewmodel = viewmodel
        }

//        viewModel.dmsLocation.observe(viewLifecycleOwner) { dmsLoc ->
//            if (dmsLoc != null) {
////                binding.viewModel.dmsFlow.value = dmsLoc
////                binding.longitudeDegreesEditText.setText(dmsLoc.longitudeDegrees.toString())
////                binding.longitudeDegreesEditText.setText(dmsLoc.longitudeMinutes.toString())
////                binding.longitudeSecondsEditText.setText(dmsLoc.longitudeSeconds.toString())
////                binding.longitudeCardinalDirectionSwitch.isChecked = !dmsLoc.longitudeWest
//
//                Log.e("TAG", "Updated location: ${dmsLoc.latitudeDegrees}, ${dmsLoc.latitudeMinutes}, ${dmsLoc.latitudeSeconds}")
//                Log.e("TAG", "Updated location: ${dmsLoc.longitudeDegrees}, ${dmsLoc.longitudeMinutes}, ${dmsLoc.longitudeSeconds}")
//            }
//        }

//        binding.latitudeDegreesInput.editText?.setText("23")
    }

//    interface LocationDmsDialogListener {
//        fun onDmsEditConfirmed()
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.e("onCreateDialog", "onCreateDialog start")

        val view = onCreateView(requireActivity().layoutInflater, null, savedInstanceState)

        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.tool_edit_location))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
//                val listener: LocationDmsDialogListener? = targetFragment as LocationDmsDialogListener?
//                listener?.onDmsEditConfirmed()

                val fm: FragmentManager = parentFragmentManager
                val args = Bundle()
                fm.setFragmentResult(DeploymentEditorFragment.EDIT_POSITION_FRAGMENT_RESULT_REQUEST_KEY, args)

                dismiss()
            }
        builder.setView(view)
        Log.e("onCreateDialog", "onCreateDialog finished")

        return builder.create()
    }

    fun setLocationToCurrentPosition() {
        val tracker = GpsLocationTracker(requireContext())
        if (tracker.canGetLocation()) {

            val loc = tracker.location
            if (loc != null) {
//                val dmsLocation = DMSLocation.fromLocation(loc)
//                viewModel.latitudeDegrees.value = loc.latitude.toInt().toString()
//                binding.latitudeDegreesEditText.setText("2")
//                binding.latitudeMinutesEditText.setText("4")
//                binding.latitudeSecondsEditText.setText(dmsLocation.latitudeSeconds.toString())
//                binding.latitudeCardinalDirectionSwitch.isChecked = !dmsLocation.latitudeSouth

                viewModel.viewModelScope.launch {
                    viewModel.setNewLocation(loc)
                }

//                Log.e("setLocationToCurr", "Updated location: ${dmsLocation.latitudeDegrees}, ${dmsLocation.latitudeMinutes}, ${dmsLocation.latitudeSeconds}")
//                Log.e("setLocationToCurr", "Updated location: ${dmsLocation.longitudeDegrees}, ${dmsLocation.longitudeDegrees}, ${dmsLocation.longitudeDegrees}")
            }

        } else {
            tracker.showSettingsAlert()
        }
    }

    companion object {
        fun newInstance(title: String?): LocationDmsDialogFragment {
            val fragment = LocationDmsDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }

}