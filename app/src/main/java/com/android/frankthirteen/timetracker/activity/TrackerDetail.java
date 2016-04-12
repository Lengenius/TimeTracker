package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TimeTrackerOpenHelper;

import java.util.Timer;
import java.util.TimerTask;

public class TrackerDetail extends Activity {
    private TimeTrackerOpenHelper timeTrackerOpenHelper;
    private final static int MSG_SHOW_TIME = 1;
    private TextView timerText;
    private ImageButton btnStart,btnStop,btnPause;
    private int timerSeconds = 0;

    private Timer timer = new Timer();
    private TimerTask timerTask=null;
    private TimerTask showTimerTask=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listitem_tracker);

        timeTrackerOpenHelper = new TimeTrackerOpenHelper(this, "TimeTracker.db", null, 1);

        btnStart = (ImageButton) findViewById(R.id.listitem_tracker_btnStart);
        btnStop = (ImageButton) findViewById(R.id.listitem_tracker_btnStop);
        btnPause = (ImageButton) findViewById(R.id.listitem_tracker_btnPause);
        btnPause.setVisibility(View.INVISIBLE);
        timerText = (TextView) findViewById(R.id.listitem_tracker_duration);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimer();
                //popup a dialog, stop timer, save data, in the dialog confirm to delete list item.
                btnStart.setVisibility(View.VISIBLE);
            }
        });

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_SHOW_TIME:
                    timerText.setText(String.format("%d:%d:%d",timerSeconds/60/60,timerSeconds/60%60,timerSeconds%60));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        timerTask.cancel();
        super.onDestroy();
    }

    private void startTimer() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerSeconds++;
                }
            };
        }
        showTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_SHOW_TIME);
            }
        };
        timer.schedule(showTimerTask, 1000, 1000);
        timer.schedule(timerTask, 1000, 1000);
    }

    private  void stopTimer(){
        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }

    private void saveTimer(){
        //save timeDuration into db it should bind to trackerDuration

        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }


}
