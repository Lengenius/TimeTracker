package com.android.frankthirteen.timetracker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.frankthirteen.timetracker.fragment.ReporterChartFragment;
import com.android.frankthirteen.timetracker.fragment.ReporterModifyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ReporterPagerAdapter(FragmentManager fm, UUID uuid) {
        super(fm);
        if (fragments == null) {
            fragments = new ArrayList<Fragment>();
        }
        fragments.add(ReporterChartFragment.newInstance(uuid));
        fragments.add(ReporterModifyFragment.newInstance(uuid));
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ChartView";
            case 1:
                return "Reporter View";
            default:
                return "null";
        }
    }
}
