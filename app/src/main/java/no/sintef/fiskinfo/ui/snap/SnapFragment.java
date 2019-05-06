package no.sintef.fiskinfo.ui.snap;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import no.sintef.fiskinfo.R;

public class SnapFragment extends Fragment  {

    ViewPager viewPager;
    SnapPageAdapter snapPagerAdapter;
//    private SnapInboxViewModel mViewModel;

    public static SnapFragment newInstance() {
        return new SnapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_snap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        snapPagerAdapter = new SnapPageAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.snappager);
        viewPager.setAdapter(snapPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.snaptab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(SnapInboxViewModel.class);
        // TODO: Use the ViewModel
    }

    public class SnapPageAdapter extends FragmentPagerAdapter {


        public SnapPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.snap_tab_titles)[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return EchogramFragment.newInstance(position + 1);

            } else {
                return SnapInboxFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
