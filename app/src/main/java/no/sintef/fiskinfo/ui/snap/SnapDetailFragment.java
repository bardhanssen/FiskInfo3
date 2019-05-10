package no.sintef.fiskinfo.ui.snap;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
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
                mBinding.setEchogram(snap.echogram);
                mBinding.setHandlers(SnapDetailFragment.this);
            }
        });
    }

    public void onViewEchogramHereClicked(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(mViewModel.getSelectedSnap().getValue().echogram.echogramURL);
        startActivity(i);
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
