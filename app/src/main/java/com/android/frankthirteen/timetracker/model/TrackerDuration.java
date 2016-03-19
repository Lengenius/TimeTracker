package com.android.frankthirteen.timetracker.model;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Frank on 3/18/16.
 */
public class TrackerDuration {

    TrackerDuration(long id, long startTime){
        this.id = id;
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public long getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void saveTime(int duration){
        this.duration = duration;
    }



    private long id;
    private long startTime;
    private int duration; //This is a duration of a whole activity
    private int timerSeconds;

}
