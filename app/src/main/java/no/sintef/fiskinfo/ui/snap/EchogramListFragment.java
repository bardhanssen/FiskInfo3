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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.EchogramInfo;

/**
 * A fragment showing a list of Echograms.
 * <p/>
 */
public class EchogramListFragment extends Fragment implements EchogramRecyclerViewAdapter.OnEchogramInteractionListener {

    private SnapViewModel mSnapViewModel;
    private EchogramViewModel mEchogramViewModel;
    private EchogramRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EchogramListFragment() {
    }

    public static EchogramListFragment newInstance() {
        return new EchogramListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSnapViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mEchogramViewModel = ViewModelProviders.of(getActivity()).get(EchogramViewModel.class);
        mEchogramViewModel.getEchogramInfos().observe(this, new Observer<List<EchogramInfo>>() {
            @Override
            public void onChanged(List<EchogramInfo> echogramInfos) {
                mAdapter.setEchograms(echogramInfos);
                if (mSwipeLayout != null)
                    mSwipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.echogram_list_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.echogram_list);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new EchogramRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.echogramlistswipelayout);

        mSwipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mEchogramViewModel.refreshEchogramListContent();
                    }
                }
        );

        return view;
    }

    @Override
    public void onViewEchogramClicked(View v, EchogramInfo echogram) {
        if (echogram != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(echogram.echogramUrl));
            startActivity(i);
        }
    }

    @Override
    public void onShareEchogramClicked(View v, EchogramInfo echogram) {
        mSnapViewModel.createDraftFrom(echogram);
        Navigation.findNavController(v).navigate(R.id.action_snap_fragment_to_newSnapFragment);
    }
}
