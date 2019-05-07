package no.sintef.fiskinfo.ui.snap;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.databinding.FragmentSnapEditorBinding;
import no.sintef.fiskinfo.model.Snap;

public class SnapEditorFragment extends Fragment {

    private SnapViewModel mViewModel;
    private FragmentSnapEditorBinding mBinding;

    public static SnapEditorFragment newInstance() {
        return new SnapEditorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_snap_editor, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getDraft().observe(this, new Observer<Snap>() {
            @Override
            public void onChanged(Snap snap) {
                mBinding.setSnap(snap);
                mBinding.setEchogram(snap.echogram);
            }
        });
    }

}
