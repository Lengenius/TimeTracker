package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.adapter.ReporterPagerAdapter;
import com.android.frankthirteen.timetracker.entities.Tracker;

import java.util.UUID;

public class ReporterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvIndicator1,tvIndicator2;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
//    private ReporterPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter);

        UUID uuid = (UUID) getIntent().getSerializableExtra(Tracker.EXTRA_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvIndicator1 = ((TextView) findViewById(R.id.indicator_1));
        tvIndicator2 = ((TextView) findViewById(R.id.indicator_2));

        tvIndicator1.setOnClickListener(this);
        tvIndicator2.setOnClickListener(this);


        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.reporter_container);
        mViewPager.setAdapter(new ReporterPagerAdapter(getSupportFragmentManager(),uuid));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_settings:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.indicator_1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.indicator_2:
                mViewPager.setCurrentItem(1);
                break;
            default:

                break;
        }
    }
}
