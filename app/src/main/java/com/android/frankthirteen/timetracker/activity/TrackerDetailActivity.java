package com.android.frankthirteen.timetracker.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.frankthirteen.timetracker.fragment.DetailFragment;

public class TrackerDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new DetailFragment();
    }
}