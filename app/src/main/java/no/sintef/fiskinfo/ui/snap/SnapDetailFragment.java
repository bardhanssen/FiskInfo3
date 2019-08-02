/**
 * Copyright (C) 2019 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.ui.snap;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.databinding.SnapDetailFragmentBinding;
import no.sintef.fiskinfo.model.SnapMessage;

public class SnapDetailFragment extends Fragment {

    private SnapViewModel mViewModel;
    private SnapDetailFragmentBinding mBinding;

    public static SnapDetailFragment newInstance() {
        return new SnapDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.snap_detail_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getSelectedSnap().observe(this, new Observer<SnapMessage>() {
            @Override
            public void onChanged(SnapMessage snap) {
                mBinding.setSnap(snap);
                mBinding.setEchogram(snap.echogramInfo);
                mBinding.setHandlers(SnapDetailFragment.this);
            }
        });
    }

    public void onViewEchogramHereClicked(View v) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mViewModel.getSelectedSnap().getValue().echogramInfo.echogramUrl));
            startActivity(i);
        } catch (Exception ex) {
        }
    }

    public void onViewInMapClicked(View v) {
        Toast toast = Toast.makeText(this.getContext(),"Not yet implemented!",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onViewInEchosounderClicked(View v) {
        Toast toast = Toast.makeText(this.getContext(),"Not yet implemented!",Toast.LENGTH_SHORT);
        toast.show();
    }

}
