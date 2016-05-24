package com.android.frankthirteen.timetracker.activity;


import android.support.v4.app.Fragment;

import com.android.frankthirteen.timetracker.fragment.TrackerListFragment;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TrackerListFragment();
    }
}
