package com.android.frankthirteen.timetracker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.frankthirteen.timetracker.fragment.ReporterChartFragment;
import com.android.frankthirteen.timetracker.fragment.ReporterModifyFragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    private UUID trackerId;

    public ReporterPagerAdapter(FragmentManager fm,UUID uuid) {
        super(fm);

        fragments.add(ReporterChartFragment.newInstance(uuid));
        fragments.add(ReporterModifyFragment.newInstance(uuid));
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;


        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "ChartView";
            case 1:
                return "Reporter View";
            default:
                return "null";
        }
    }
}
