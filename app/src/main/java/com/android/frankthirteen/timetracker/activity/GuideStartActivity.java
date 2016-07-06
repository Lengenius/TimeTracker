package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.fragment.TrackListFragment;
import com.android.frankthirteen.timetracker.fragment.WorkFocusFragment;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.UUID;

public class GuideStartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public SharedPreferences preferences = null;

    private static final int REQUEST_TRACKER = 0x0001;
    private static final String TAG = "GUIDE";
    private static final String FIRST_RUN = "FirstRun";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;

    private boolean doubleClickToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("com.android.frankthirteen.timetracker",MODE_PRIVATE);

        setContentView(R.layout.activity_guide_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.guide_fragment_container);

        if (fragment == null) {
            fragment = WorkFocusFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.guide_fragment_container, fragment).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferences.getBoolean(FIRST_RUN,true)){
            preferences.edit().putBoolean(FIRST_RUN,false).apply();
            // When first run load the show case view.

            Target target = new ViewTarget(R.id.btn_start,this);
            firstLaunch(target);


        }

    }

    private ShowcaseView firstLaunch(Target target) {

        return new ShowcaseView.Builder(this)
                .setContentTitle("Start ")
                .setContentText("Press start button to start.")
                .setTarget(target)
                .setStyle(R.style.CustomShowcaseTheme)
                .hideOnTouchOutside()
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!(fragment.getClass()).equals(WorkFocusFragment.class)) {
            fragment = WorkFocusFragment.newInstance();
            replaceFragment();
            LogUtils.d("GUIDE", "not matches");
        } else {
            doubleClickToQuit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guide_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:

                break;
            case R.id.action_add_new_tracker:
                Intent intent = new Intent(getApplicationContext(), DefineTrackerActivity.class);
                startActivityForResult(intent, REQUEST_TRACKER);

                break;
            //add new tracker to tracker list.
            default:

                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_list_fragment:
                fragment = TrackListFragment.newInstance();
                break;
            case R.id.detail_fragment:
                break;
            case R.id.nav_work_fragment:
                fragment = WorkFocusFragment.newInstance();
                break;
            case R.id.nav_send:

                break;
            case R.id.nav_share:

                break;
            default:
                fragment = WorkFocusFragment.newInstance();
                break;
        }

        replaceFragment();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case REQUEST_TRACKER:
                LogUtils.d(TAG, "tracker result OK");
                UUID trackerId = (UUID) data.getSerializableExtra(Tracker.EXTRA_ID);
                Tracker tracker = TrackerLab.getTrackerLab(GuideStartActivity.this).
                        getTracker(trackerId);
                TrackerDB.getTrackerDB(GuideStartActivity.this).insertTracker(tracker);
                if ((fragment.getClass()).equals(TrackListFragment.class)){

                }

                break;
            default:

                break;
        }
    }

    private void replaceFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.guide_fragment_container, fragment).commit();

    }


    private void doubleClickToQuit() {
        if (doubleClickToExit) {
            super.onBackPressed();
        }

        doubleClickToExit = true;
        Toast.makeText(GuideStartActivity.this, "Double click to quit TimeTracker.",
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClickToExit = false;
            }
        }, 1200);
    }

}
