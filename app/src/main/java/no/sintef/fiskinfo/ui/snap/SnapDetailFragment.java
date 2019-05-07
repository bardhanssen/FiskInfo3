package no.sintef.fiskinfo.ui.snap;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.databinding.FragmentSnapDetailBinding;
import no.sintef.fiskinfo.model.Snap;

public class SnapDetailFragment extends Fragment {

    private SnapViewModel mViewModel;
    private FragmentSnapDetailBinding mBinding;

    public static SnapDetailFragment newInstance() {
        return new SnapDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_snap_detail, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getSelectedSnap().observe(this, new Observer<Snap>() {
            @Override
            public void onChanged(Snap snap) {
                mBinding.setSnap(snap);
                mBinding.setEchogram(snap.echogram);
            }
        });
    }

}
