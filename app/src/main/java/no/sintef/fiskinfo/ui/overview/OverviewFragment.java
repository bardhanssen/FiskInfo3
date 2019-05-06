package no.sintef.fiskinfo.ui.overview;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;
import no.sintef.fiskinfo.R;

public class OverviewFragment extends Fragment {

    private OverviewViewModel mViewModel;

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OverviewViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button mapB = view.findViewById(R.id.proto_map_button);
        mapB.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_overview_fragment_to_map_fragment, null));

        Button toolsB = view.findViewById(R.id.proto_tools_button);
        toolsB.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_overview_fragment_to_tools_fragment, null));
    }
}
