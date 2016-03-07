package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.frankthirteen.timetracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_tracker);
    }

}
