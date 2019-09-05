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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.SnapDetailFragmentBinding
import no.sintef.fiskinfo.model.SnapMessage
import no.sintef.fiskinfo.repository.SnapRepository

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
            mBinding!!.setEchogram(snap?.echogramInfo)
            mBinding!!.setHandlers(this@SnapDetailFragment)
        })
    }

    fun onViewEchogramHereClicked(v: View) {
        try {
            if (! (mViewModel?.getSelectedSnap()?.value?.echogramInfo?.snapId != null))
                return
            var snapId = mViewModel?.getSelectedSnap()?.value?.echogramInfo?.snapId.toString()
            var bundle = bundleOf(ARG_SNAP_ID to snapId)
            v.findNavController().navigate(R.id.action_snapDetailFragment_to_echogramViewerFragment, bundle)

            /*
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val snapFishServerUrl = prefs.getString("server_address", SnapRepository.DEFAULT_SNAP_FISH_SERVER_URL)
            if (snapFishServerUrl != null) {
                val snapFishWebServerUrl = snapFishServerUrl.replace("5002", "5006").replace("http:", "https:")
                val i = Intent(Intent.ACTION_VIEW)
                val url = snapFishWebServerUrl + "snap/" + mViewModel?.getSelectedSnap()?.value?.echogramInfo?.snapId.toString()
                i.data = Uri.parse(url)
                startActivity(i)
            }*/

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

/*
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import no.sintef.fiskinfo.R

class SnapDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SnapDetailFragment()
    }

    private lateinit var viewModel: SnapDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.snap_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SnapDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

*/