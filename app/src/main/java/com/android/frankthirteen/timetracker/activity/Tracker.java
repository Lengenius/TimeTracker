package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TimeTrackerOpenHelper;

import java.util.Timer;
import java.util.TimerTask;

public class Tracker extends Activity {
    private TimeTrackerOpenHelper timeTrackerOpenHelper;
    private final static int MSG_SHOW_TIME = 1;
    private TextView timerText;
    private Button btnStart;
    private Button btnStop;
    private Button btnPause;
    private int timerSeconds = 0;

    private Timer timer = new Timer();
    private TimerTask timerTask=null;
    private TimerTask showTimerTask=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tracker_item);

        timeTrackerOpenHelper = new TimeTrackerOpenHelper(this, "TimeTracker.db", null, 1);

        btnStart = (Button) findViewById(R.id.trackerBtnStart);
        btnStop = (Button) findViewById(R.id.trackerBtnStop);
        btnPause = (Button) findViewById(R.id.trackerBtnPause);
        btnPause.setVisibility(View.INVISIBLE);
        timerText = (TextView) findViewById(R.id.tracker_item_timer);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnStart.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimer();
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

    //    String.format("%d:%d:%d",timerSeconds/60/60,timerSeconds/60%60,timerSeconds%60)
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
        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }


}
