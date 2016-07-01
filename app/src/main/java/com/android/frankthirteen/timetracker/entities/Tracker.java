package com.android.frankthirteen.timetracker.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 * Tracker means a task you may want to achieve in a certain time.
 * When you are done with it. You can remove it from tracking list.
 */
public class Tracker {
    public static final String EXTRA_ID = "com.android.frankthirteen.timetracker.Extra_id";
    public static final String EXTRA_DATE = "com.android.frankthirteen.timetracker.Extra_date";

    private UUID trackerId;
    private String mTrackerTitle;
    private String mTrackerContent;
    private String mComment;
    private String mPhotoPath;
    private int durations;
    private int plannedTimeInMinutes;
    private boolean tracking;

    private Date mEndDate,mStartDate;

    private List<DurationItem> durationItems;

    public Tracker() {
        this.trackerId = UUID.randomUUID();
        tracking = true;
        durationItems = new ArrayList<>();
    }

    public Tracker(UUID uuid) {
        trackerId = uuid;
        tracking = true;
        mStartDate = new Date();
    }

//    public String getTag() {
//        return mTag;
//    }
//
//    public void setTag(String mTag) {
//        this.mTag = mTag;
//    }

    public List<DurationItem> getDurationItems() {
        return durationItems;
    }


    public void addDuration(DurationItem di) {
        di.setTrackerId(trackerId);
        durationItems.add(di);
        updateTotalDuration();
    }

    public int getTotalDurations() {
        return durations;
    }

    public void updateTotalDuration() {
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

    @Override
    public String toString() {
        return mTrackerTitle;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public void setPlanningTime(int hours) {
        plannedTimeInMinutes = hours * 60;
    }

    public int getPlannedTimeInMinutes() {
        return plannedTimeInMinutes;
    }

    public Date getEndDate() {
        if (mEndDate != null) {
            return mEndDate;
        } else {
            return null;
        }
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String mPhotoPath) {
        this.mPhotoPath = mPhotoPath;
    }

    public void setStartDate(Date startDate){
        mStartDate = startDate;
    }

    public Date getStartDate() {
        return mStartDate;
    }
}
