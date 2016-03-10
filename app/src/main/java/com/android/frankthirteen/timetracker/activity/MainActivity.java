package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.utils.TrackerItem;
import com.android.frankthirteen.timetracker.utils.TrackerItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TrackerItem> timeTrackerList = new ArrayList<TrackerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTrackerList();
        TrackerItemAdapter adapter = new TrackerItemAdapter(MainActivity.this, R.layout.tracker_item,timeTrackerList);
        ListView listView = (ListView) findViewById(R.id.time_tracker_listview);
        listView.setAdapter(adapter);
    }

    private void testTrackerList(){
        TrackerItem item1 = new TrackerItem("title1","Content1",R.mipmap.ic_launcher,0);
        timeTrackerList.add(item1);
        TrackerItem item2 = new TrackerItem("title2","Content2",R.mipmap.ic_launcher,0);
        timeTrackerList.add(item2);
    }

}
