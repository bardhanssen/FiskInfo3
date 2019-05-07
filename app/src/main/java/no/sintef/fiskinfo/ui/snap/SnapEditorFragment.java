package no.sintef.fiskinfo.ui.snap;

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
import no.sintef.fiskinfo.model.Snap;

public class SnapEditorFragment extends Fragment {

    private SnapViewModel mViewModel;
    private View mView;

    public static SnapEditorFragment newInstance() {
        return new SnapEditorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_snap_editor, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getDraft().observe(this, new Observer<Snap>() {
            @Override
            public void onChanged(Snap snap) {
                TextView timeText = mView.findViewById(R.id.timeTextView);
                timeText.setText(snap.echogram.timestamp.toString());
                TextView positionText = mView.findViewById(R.id.positionTextView);
                positionText.setText(snap.echogram.location);
            }
        });
    }

}
