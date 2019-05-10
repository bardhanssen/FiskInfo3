package no.sintef.fiskinfo.ui.snap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.EchogramInfo;
import no.sintef.fiskinfo.repository.EchogramRepository;

/**
 * A fragment showing a list of Echograms.
 * <p/>
 */
public class EchogramListFragment extends Fragment implements EchogramRecyclerViewAdapter.OnEchogramInteractionListener {

    private SnapViewModel mSnapViewModel;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.echogram_list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new EchogramRecyclerViewAdapter(new EchogramRepository().getEchograms(), this));
        }
        return view;
    }

    @Override
    public void onViewEchogramClicked(View v, EchogramInfo echogram) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(echogram.echogramURL);
        startActivity(i);
    }

    @Override
    public void onShareEchogramClicked(View v, EchogramInfo echogram) {
        mSnapViewModel.createDraftFrom(echogram);
        Navigation.findNavController(v).navigate(R.id.action_snap_fragment_to_newSnapFragment);
    }
}
