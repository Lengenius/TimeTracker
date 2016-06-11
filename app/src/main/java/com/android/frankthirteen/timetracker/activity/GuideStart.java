package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
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
import com.android.frankthirteen.timetracker.fragment.DetailFragment;
import com.android.frankthirteen.timetracker.fragment.TrackListFragment;
import com.android.frankthirteen.timetracker.fragment.WorkFocusFragment;

public class GuideStart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (fragment==null){
            fragment = WorkFocusFragment.newInstance();
        }

        fragmentManager.beginTransaction().add(R.id.guide_fragment_container,fragment).commit();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.list_fragment:
                fragment = TrackListFragment.newInstance();
                break;
            case R.id.detail_fragment:
                fragment = DetailFragment.newInstance();
                break;
            case R.id.work_fragment:
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

        replaceFragment(fragment);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.guide_fragment_container,frag).commit();
    }
}
