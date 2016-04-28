package com.android.frankthirteen.timetracker.model;

import android.content.Context;

import com.android.frankthirteen.timetracker.db.TimeTrackerDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 * As this class is becoming bigger and bigger, I feel like to abstract it and break into small pieces.
 */
public class TrackerItem {
    private int mDuration = 0;
    private int tempDuration;
    private Context mContext;
    private UUID mId;
    private Boolean mIsStarted = false;
    private Date defaultDate;
    private Photo mPhoto = null;
    private String mTitle, mContent, mCommit;
    private String trackingState;
    private List<DurationItem> mDurationItems;

    public TrackerItem(Context context) {
        mId = UUID.randomUUID();
        mContext = context;
        defaultDate = new Date();
        trackingState = "Tracking";
        //Test constructor
        mTitle = "Hello";
        mContent = "Tracker";
        mDurationItems = new ArrayList<DurationItem>();
    }

    public TrackerItem(){
        mId = UUID.randomUUID();
        defaultDate = new Date();
        trackingState = "Tracking";
        //Test constructor
        mTitle = "Hello";
        mContent = "Tracker";
        mDurationItems = new ArrayList<DurationItem>();
    }

    /**
     * Check if the trackerItem is started.
     * @return
     */
    public Boolean isStarted() {
        return mIsStarted;
    }

    /**
     * increase the inside counter.
     */
    public void increase() {
        tempDuration++;
        mDuration++;
    }

    /**
     * get the
     * @return trackerItem id.
     */
    public UUID getmId() {
        return mId;
    }

    /**
     * get the total duration of the whole trackerItem.
     * @return
     */
    public int getmDuration() {
        return mDuration;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    /**
     * start or stop the tracker item depends on the argument passed in.
     * @param b
     */
    public void setmIsStarted(Boolean b) {
        mIsStarted = b;
        if (b){
            tempDuration = 0;
        }
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public void setmDurationItems(List<DurationItem> mDurationItems) {
        this.mDurationItems = mDurationItems;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }

    public void setTrackingState(String trackingState) {
        this.trackingState = trackingState;
    }

    public void setmCommit(String mCommit) {
        this.mCommit = mCommit;
    }

    public String getmCommit() {
        return mCommit;
    }

    public List<DurationItem> getmDurationItems() {
        return mDurationItems;
    }

    public void saveDuration() {
        //used when item pause;
        DurationItem durationItem = new DurationItem(new Date(), tempDuration, mId);
        mDurationItems.add(durationItem);
        TimeTrackerDB db = TimeTrackerDB.getInstance(mContext);
        db.saveDurationItem(durationItem);
    }


    public Date getStartDate() {
        Date startDate;
        if (mDurationItems.size() == 0) {
            startDate = defaultDate;
        } else {
            DurationItem mDurationItem = mDurationItems.get(0);
            Date endDate = mDurationItem.getmEndDate();
            int duration = mDurationItem.getmDuration();
            startDate = new Date(endDate.getTime() - duration);
        }
        return startDate;
    }

    public Date getEndDate() {
        Date endDate;
        if (mDurationItems.size() == 0) {
            endDate = defaultDate;
        } else {
            endDate = mDurationItems.get(mDurationItems.size() - 1).getmEndDate();
        }
        return endDate;
    }

    public Photo getmPhoto() {
        return mPhoto;
    }

    public String getTrackingState() {
        return trackingState;
    }
}
