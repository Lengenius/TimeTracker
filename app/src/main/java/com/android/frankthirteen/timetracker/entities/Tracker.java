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
    private String mTag;
    private int durations;
    private boolean tracking;

    private ArrayList<DurationItem> durationItems;

    public Tracker() {
        this.trackerId = UUID.randomUUID();
        tracking = true;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public ArrayList<DurationItem> getDurationItems() {
        return durationItems;
    }


    public void addDuration(DurationItem di){
        di.setTrackerId(trackerId);
        durationItems.add(di);
    }

    public int getTotalDurations() {
        return durations;
    }
    public void updateTotalDuration(){
        durations = 0;
        for (DurationItem di :
                durationItems) {
            durations += di.getmDuration();
        }
    }

    public UUID getId() {
        return trackerId;
    }

    public void setId(UUID trackerId) {
        this.trackerId = trackerId;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    public String getTitle() {
        return mTrackerTitle;
    }

    public void setTitle(String mTrackerTitle) {
        this.mTrackerTitle = mTrackerTitle;
    }

    public String getContent() {
        return mTrackerContent;
    }

    public void setContent(String mTrackerContent) {
        this.mTrackerContent = mTrackerContent;
    }

    public String getGoal() {
        return mTrackerGoal;
    }

    public void setGoal(String mTrackerGoal) {
        this.mTrackerGoal = mTrackerGoal;
    }

    public void addDurationItem(DurationItem di){
        durationItems.add(di);
    }
}
