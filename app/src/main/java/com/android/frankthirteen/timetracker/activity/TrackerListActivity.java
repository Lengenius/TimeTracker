package com.android.frankthirteen.timetracker.activity;

import android.app.Fragment;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TrackerListFragment();
    }
}
