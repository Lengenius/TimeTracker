package com.android.frankthirteen.timetracker.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItem {
    public static final int MSG_SHOW_TIMER = 1;
    private Timer timer = new Timer();
    private TimerTask timerTask = null;
    private TimerTask showTimerTask = null;


    private String title;
    private String content;
    private int itemImage;

    private int timerSeconds;

    public TrackerItem (String title, String content, int itemImage, int timerSeconds) {
        this.title = title;
        this.content = content;
        this.itemImage = itemImage;
        this.timerSeconds = timerSeconds;
    }

    public String getTitle(){
        return title;
    }

    public  String getContent(){
        return content;
    }

    public int getItemImage(){
        return itemImage;
    }

    public int getTimerSeconds(){
        return timerSeconds;
    }

    public void startTimer(final Handler handler){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timerSeconds++;
            }
        };
        showTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_SHOW_TIMER);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
        timer.schedule(showTimerTask, 1000, 1000);
    }

    public void saveTimerSeconds(int seconds){
        timerSeconds = seconds;
    }
}
