package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.fragment.ReporterModifyFragment;

import java.util.UUID;

/**
 * Created by Frank on 6/24/16.
 */
public class CreateTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);
        if (fragment==null){
            fragment = ReporterModifyFragment.newInstance(UUID.randomUUID());
            fm.beginTransaction().add(R.id.single_fragment_container,fragment).commit();
        }

        fm.beginTransaction().replace(R.id.single_fragment_container,fragment).commit();
    }
}
