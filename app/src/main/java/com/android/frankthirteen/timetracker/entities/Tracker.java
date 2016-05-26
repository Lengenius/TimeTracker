package com.android.frankthirteen.timetracker.entities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 * Tracker means a task you may want to achieve in a certain time.
 * When you are done with it. You can remove it from tracking list.
 */
public class Tracker {
    private UUID trackerId;
    private String mTrackerTitle;
    private String mTrackerContent;
    private String mTrackerGoal;
    private int durations;
    private ArrayList<DurationItem> durationItems;

    private boolean tracking;

    public Tracker() {
        this.trackerId = UUID.randomUUID();
    }

    public ArrayList<DurationItem> getDurationItems() {
        return durationItems;
    }


    public void addDuration(DurationItem di){
        di.setTrackerId(trackerId);
        durationItems.add(di);
    }

    public int getDurations() {
        durations = 0;
        for (DurationItem di :
                durationItems) {
            durations += di.getmDuration();
        }
        return durations;
    }

    public UUID getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(UUID trackerId) {
        this.trackerId = trackerId;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public String getmTrackerTitle() {
        return mTrackerTitle;
    }

    public void setmTrackerTitle(String mTrackerTitle) {
        this.mTrackerTitle = mTrackerTitle;
    }

    public String getmTrackerContent() {
        return mTrackerContent;
    }

    public void setmTrackerContent(String mTrackerContent) {
        this.mTrackerContent = mTrackerContent;
    }

    public String getmTrackerGoal() {
        return mTrackerGoal;
    }

    public void setmTrackerGoal(String mTrackerGoal) {
        this.mTrackerGoal = mTrackerGoal;
    }
}
