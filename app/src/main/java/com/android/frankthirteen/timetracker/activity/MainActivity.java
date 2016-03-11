package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.utils.TrackerItem;
import com.android.frankthirteen.timetracker.utils.TrackerItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.add_tracker);

        testTrackerList();
        final TrackerItemAdapter adapter = new TrackerItemAdapter(MainActivity.this, R.layout.tracker_item,timeTrackerList);
        final ListView listView = (ListView) findViewById(R.id.time_tracker_listview);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTracker();
                adapter.notifyDataSetChanged();
                listView.setSelection(timeTrackerList.size());
            }
        });
    }

    private void testTrackerList(){
        TrackerItem item1 = new TrackerItem("title1","Content1",R.mipmap.ic_launcher,0);
        timeTrackerList.add(item1);
        TrackerItem item2 = new TrackerItem("title2","Content2",R.mipmap.ic_launcher,0);
        timeTrackerList.add(item2);
    }

    private void addTracker(){
        TrackerItem item3 = new TrackerItem("title3","Content3",R.mipmap.ic_launcher,0);
        timeTrackerList.add(item3);
    }

    private List<TrackerItem> timeTrackerList = new ArrayList<TrackerItem>();
    private Button btnAdd;

}
