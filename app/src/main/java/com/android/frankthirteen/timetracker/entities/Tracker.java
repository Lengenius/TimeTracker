package com.android.frankthirteen.timetracker.entities;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 * Tracker means a task you may want to achieve in a certain time.
 * When you are done with it. You can remove it from tracking list.
 */
public class Tracker {
    public static final String EXTRA_ID = "com.android.frankthirteen.timetracker.entities.Extra_id";

    private UUID trackerId;
    private String mTrackerTitle;
    private String mTrackerContent;
    private String mTag,mComment;
    private int durations;
    private boolean tracking;

    private ArrayList<DurationItem> durationItems;

    public Tracker() {
        this.trackerId = UUID.randomUUID();
        tracking = true;
    }

    public Tracker(UUID uuid){
        trackerId = uuid;
        tracking = true;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
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
            durations += di.getDuration();
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

    public void addDurationItem(DurationItem di){
        durationItems.add(di);
    }

    @Override
    public String toString() {
        return mTrackerTitle;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public String getComment(){
        return mComment;
    }
}
