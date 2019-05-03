package no.sintef.fiskinfo.ui.snap;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.repository.EchogramRepository;
import no.sintef.fiskinfo.repository.SnapRepository;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnEchogramInteractionListener}
 * interface.
 */
public class SnapInboxFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private EchogramFragment.OnEchogramInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SnapInboxFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SnapInboxFragment newInstance(int columnCount) {
        SnapInboxFragment fragment = new SnapInboxFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.snap_inbox_fragment, container, false);

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

            recyclerView.setAdapter(new SnapRecyclerViewAdapter(new SnapRepository().getInboxSnaps(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EchogramFragment.OnEchogramInteractionListener) {
            mListener = (EchogramFragment.OnEchogramInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEchogramInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
/*TODO:    public interface OnEchogramInteractionListener {
        void onViewEchogramClicked(Echogram echogram);
        void onShareEchogramClicked(Echogram echogram);
    }*/
}