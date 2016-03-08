package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;

import java.util.Timer;
import java.util.TimerTask;

public class Tracker extends Activity {

    private final static int MSG_SHOW_TIME = 1;
    private TextView timerText;
    private ImageButton btnStart;
    private ImageButton btnStop;
    private int timerSeconds = 0;

    private Timer timer = new Timer();
    private TimerTask timerTask=null;
    private TimerTask showTimerTask=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_item_tracker);

        btnStart = (ImageButton) findViewById(R.id.timer_component_start);
        btnStop = (ImageButton) findViewById(R.id.timer_component_stop);
        timerText = (TextView) findViewById(R.id.list_item_timer);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnStart","btnStart clicked");
                timerText.setText("Changed");
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_SHOW_TIME:
                    timerText.setText("Timer Begin");
                    break;
                default:
                    break;
            }
        }
    };

//    String.format("%d:%d:%d",timerSeconds/60/60,timerSeconds/60%60,timerSeconds%60)
    private void startTimer() {
        if (timerTask != null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerSeconds+=1;
                }
            };
        }
        showTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_SHOW_TIME);
            }
        };
        timer.schedule(showTimerTask,1000);
        timer.schedule(timerTask,1000);
    }

    private  void stopTimer(){
        if (timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        timerTask.cancel();
        super.onDestroy();
    }
}
