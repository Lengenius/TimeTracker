package com.android.frankthirteen.timetracker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.fragment.DefineTrackerFragment;
import com.android.frankthirteen.timetracker.fragment.TrackListFragment;

import java.util.UUID;

/**
 * Created by Frank on 6/24/16.
 */
public class DefineTrackerActivity extends AppCompatActivity {
    private UUID trackerId;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        setTitle(getString(R.string.title_tracker_define));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        trackerId = UUID.randomUUID();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_fragment_container);
        if (fragment==null){
            fragment = DefineTrackerFragment.newInstance(trackerId);
        }
        fm.beginTransaction().add(R.id.single_fragment_container, fragment).commit();
//        else {
//            fm.beginTransaction().replace(R.id.single_fragment_container, fragment).commit();
//        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(DefineTrackerActivity.this)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Tracker.EXTRA_ID,trackerId);
                        setResult(RESULT_CANCELED,resultIntent);
                        TrackerLab.getTrackerLab(DefineTrackerActivity.this).
                                removeTracker(trackerId);
                        DefineTrackerActivity.super.onBackPressed();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Tracker.EXTRA_ID,trackerId);
                        setResult(RESULT_OK,resultIntent);
                        sendLocalBroadcast(TrackListFragment.TRACKER_ADDED);
                        DefineTrackerActivity.super.onBackPressed();
                    }
                })
                .setTitle("Exit")
                .setMessage("Are you sure you want to create this tracker?").create().show();

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

    private void sendLocalBroadcast(String action){
        Intent intent = new Intent(action);
        localBroadcastManager.sendBroadcast(intent);
    }
}
