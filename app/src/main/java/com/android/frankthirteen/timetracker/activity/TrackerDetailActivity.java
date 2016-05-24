package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.android.frankthirteen.timetracker.fragment.TrackerDetailFragment;

import java.util.UUID;

/**
 * Created by Frank on 4/17/16.
 */
public class TrackerDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID trackerItemId = (UUID) getIntent().getSerializableExtra(TrackerDetailFragment.EXTRA_TRACKER_ID);

        return TrackerDetailFragment.newInstance(trackerItemId);
    }
}
