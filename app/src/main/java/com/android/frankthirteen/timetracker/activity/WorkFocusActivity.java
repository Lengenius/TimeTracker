package com.android.frankthirteen.timetracker.activity;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.frankthirteen.timetracker.fragment.TrackerDetailFragment;
import com.android.frankthirteen.timetracker.fragment.WorkFocusFragment;

import java.util.UUID;

/**
 * Created by Frank on 5/5/16.
 */
public class WorkFocusActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID trackerItemId = (UUID) getIntent().getSerializableExtra(TrackerDetailFragment.EXTRA_TRACKER_ID);
        Log.d("Focus", "new instance");
        return WorkFocusFragment.newInstance(trackerItemId);
    }
}
