package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import com.android.frankthirteen.timetracker.R;

public class Tracker extends Activity {

    private ImageButton btnStart;
    private ImageButton btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_item_tracker);

        btnStart = (ImageButton) findViewById(R.id.timer_component_start);
        btnStop = (ImageButton) findViewById(R.id.timer_component_stop);

    }

}
