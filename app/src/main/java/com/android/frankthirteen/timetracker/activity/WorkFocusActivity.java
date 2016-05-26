package com.android.frankthirteen.timetracker.activity;

import android.support.v4.app.Fragment;

import com.android.frankthirteen.timetracker.fragment.WorkFocusFragment;

/**
 * Created by Frank on 5/24/16.
 */
public class WorkFocusActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return WorkFocusFragment.newInstance();
    }
}
