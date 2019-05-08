package no.sintef.fiskinfo.ui.snap;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.Snap;
import no.sintef.fiskinfo.repository.SnapRepository;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class SnapInboxFragment extends Fragment implements SnapRecyclerViewAdapter.OnSnapInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    // private EchogramFragment.OnEchogramInteractionListener mListener;
    private SnapViewModel mViewModel;
    private SnapRecyclerViewAdapter mAdapter;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getInboxSnaps().observe(this, new Observer<List<Snap>>() {
            @Override
            public void onChanged(List<Snap> snaps) {
                mAdapter.setSnaps(snaps);
            }
        });

/*        ViewParent parent = this.getView().getParent();
        if (parent instanceof ViewPager) {
            TabLayout tabLayout = (TabLayout) ((ViewPager) parent).findViewById(R.id.snaptab_layout);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snap_inbox, container, false);

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

            mAdapter = new SnapRecyclerViewAdapter(this);
            recyclerView.setAdapter(mAdapter); //new SnapRecyclerViewAdapter(new SnapRepository().getInboxSnaps(), mListener));
        }
/*        if (container instanceof ViewPager) {
            TabLayout tabLayout = (TabLayout) ((ViewPager) container).findViewById(R.id.snaptab_layout);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
            View customTab = inflater.inflate(R.layout.tab_with_icon_and_title, container, false);
            tabLayout.getTabAt(1).setCustomView(customTab);
            TextView tabText = customTab.findViewById(R.id.tabTextView);
            tabText.setText("Inbox");
        }*/
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof EchogramFragment.OnEchogramInteractionListener) {
            mListener = (EchogramFragment.OnEchogramInteractionListener) context;
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
    public void onViewSnapClicked(View v, Snap snap) {
        mViewModel.selectSnap(snap);
        Navigation.findNavController(v).navigate(R.id.action_fragment_snap_to_snapDetailFragment);
    }

    @Override
    public void onViewSnapInMapClicked(View v, Snap snap) {
        Toast toast = Toast.makeText(this.getContext(),"Not yet implemented!",Toast.LENGTH_SHORT);
        toast.show();
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
