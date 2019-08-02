/**
 * Copyright (C) 2019 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import com.google.android.material.tabs.TabLayout;

import no.sintef.fiskinfo.R;

/**
 * The SnapFragment is the main container fragment for the echo snap message functionality.
 * It contains tabs for showing the users own echo snaps, and for the inbox and outbox for
 * snap messages.
 */
public class SnapFragment extends Fragment  {

    ViewPager viewPager;
    SnapPageAdapter snapPagerAdapter;

    public static SnapFragment newInstance() {
        return new SnapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.snap_fragment, container, false);
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
                return EchogramListFragment.newInstance();

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
