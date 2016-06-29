package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.fragment.TrackerDefineFragment;

import java.util.UUID;

/**
 * Created by Frank on 6/24/16.
 */
public class CreateTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);
        if (fragment==null){
            fragment = TrackerDefineFragment.newInstance(UUID.randomUUID());
        }
        fm.beginTransaction().add(R.id.single_fragment_container, fragment).commit();
//        else {
//            fm.beginTransaction().replace(R.id.single_fragment_container, fragment).commit();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
