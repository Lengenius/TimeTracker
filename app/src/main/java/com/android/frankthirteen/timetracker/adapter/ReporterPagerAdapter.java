package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.fragment.ReporterChartFragment;
import com.android.frankthirteen.timetracker.fragment.ReporterTableFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    private List<Fragment> fragments;

    public ReporterPagerAdapter(Context context, FragmentManager fm, UUID uuid) {
        super(fm);
        mContext = context;
        if (fragments == null) {
            fragments = new ArrayList<Fragment>();
        }
        fragments.add(ReporterTableFragment.newInstance(uuid));
        fragments.add(ReporterChartFragment.newInstance(uuid));
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
                return mContext.getString(R.string.pager_title_detail);
            case 1:
                return mContext.getString(R.string.pager_title_chart);
            default:
                return "null";
        }
    }
}
