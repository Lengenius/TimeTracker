package com.android.frankthirteen.timetracker.model;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItem {
    public static final int MSG_SHOW_TIMER = 1;
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

    public void saveTimerSeconds(int seconds){
        timerSeconds = seconds;
    }

    private int timerSeconds = 0;
    private String title;
    private String content;
    private int itemImage;
}
