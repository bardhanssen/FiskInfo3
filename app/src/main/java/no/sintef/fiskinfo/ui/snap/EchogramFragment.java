package no.sintef.fiskinfo.ui.snap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.repository.EchogramRepository;
import no.sintef.fiskinfo.ui.snap.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class EchogramFragment extends Fragment implements EchogramRecyclerViewAdapter.OnEchogramInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    // private OnEchogramInteractionListener mListener;
    private SnapViewModel mSnapViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EchogramFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EchogramFragment newInstance(int columnCount) {
        EchogramFragment fragment = new EchogramFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSnapViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_echogram_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter(new MyEchogramRecyclerViewAdapter(DummyContent.ITEMS, mListener))

            recyclerView.setAdapter(new EchogramRecyclerViewAdapter(new EchogramRepository().getEchograms(), this));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnEchogramInteractionListener) {
            mListener = (OnEchogramInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEchogramInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onViewEchogramClicked(View v, Echogram echogram) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(echogram.echogramURL);
        startActivity(i);
    }

    @Override
    public void onShareEchogramClicked(View v, Echogram echogram) {
        mSnapViewModel.createDraftFrom(echogram);
        Navigation.findNavController(v).navigate(R.id.action_snap_fragment_to_newSnapFragment);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnEchogramInteractionListener {
        void onViewEchogramClicked(Echogram echogram);
        void onShareEchogramClicked(Echogram echogram);
    }*/
}
