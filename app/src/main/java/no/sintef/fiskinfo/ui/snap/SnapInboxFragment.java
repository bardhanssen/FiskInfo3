package no.sintef.fiskinfo.ui.snap;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.model.SnapMessage;

/**
 * A fragment showing the inbox of snap messages.
 * <p/>
 */
public class SnapInboxFragment extends Fragment implements SnapRecyclerViewAdapter.OnSnapInteractionListener {

    private SnapViewModel mViewModel;
    private SnapRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SnapInboxFragment() {
    }

    public static SnapInboxFragment newInstance(int columnCount) {
        return new SnapInboxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getInboxSnaps().observe(this, new Observer<List<SnapMessage>>() {
            @Override
            public void onChanged(List<SnapMessage> snaps) {
                mAdapter.setSnaps(snaps);
                if (mSwipeLayout != null)
                    mSwipeLayout.setRefreshing(false);
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
        View view = inflater.inflate(R.layout.snap_inbox_fragment, container, false);

        RecyclerView listView = view.findViewById(R.id.inbox_list);
        Context context = view.getContext();
        listView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new SnapRecyclerViewAdapter(this);
        listView.setAdapter(mAdapter);

        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.inboxswipelayout);
        //swipeLayout.setProgressBackgroundColorSchemeResource(R.color.colorBrn);

        mSwipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mViewModel.refreshInboxContent();
                    }
                }
        );



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
    public void onViewSnapClicked(View v, SnapMessage snap) {
        mViewModel.selectSnap(snap);
        Navigation.findNavController(v).navigate(R.id.action_fragment_snap_to_snapDetailFragment);
    }

    @Override
    public void onViewSnapInMapClicked(View v, SnapMessage snap) {
        Toast toast = Toast.makeText(this.getContext(),"Not yet implemented!",Toast.LENGTH_SHORT);
        toast.show();
    }
}
