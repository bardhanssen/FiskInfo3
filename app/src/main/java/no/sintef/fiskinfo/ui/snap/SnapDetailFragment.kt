package no.sintef.fiskinfo.ui.snap

/**
 * Copyright (C) 2019 SINTEF
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

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

import no.sintef.fiskinfo.R

class SnapDetailFragment : Fragment() {

    private var mViewModel: SnapViewModel? = null
    private var mBinding: no.sintef.fiskinfo.databinding.SnapDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.snap_detail_fragment, container, false)
        return mBinding!!.getRoot()
    }

    // Changed from ViewDataBinding to SnapDetailFragmentBinding above

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!).get(SnapViewModel::class.java)
        mViewModel!!.getSelectedSnap().observe(this, Observer { snap ->
            mBinding!!.setSnap(snap)
            mBinding!!.setSnapMetadata(snap?.snapMetadata)
            mBinding!!.setIncomming(mViewModel!!.isIncomming().value)
            mBinding!!.setHandlers(this@SnapDetailFragment)
        })
    }

    fun onViewEchogramHereClicked(v: View) {
        try {
            if (! (mViewModel?.getSelectedSnap()?.value?.snapMetadata?.snapId != null))
                return
            var snapId = mViewModel?.getSelectedSnap()?.value?.snapMetadata?.snapId.toString()
            var bundle = bundleOf(ARG_SNAP_ID to snapId)
            v.findNavController().navigate(R.id.action_snapDetailFragment_to_echogramViewerFragment, bundle)
        } catch (ex: Exception) {
        }

    }

    fun onViewInMapClicked(v: View) {
        val toast = Toast.makeText(this.context, "Not yet implemented!", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onViewInEchosounderClicked(v: View) {
        val toast = Toast.makeText(this.context, "Not yet implemented!", Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {

        fun newInstance(): SnapDetailFragment {
            return SnapDetailFragment()
        }
    }

}
