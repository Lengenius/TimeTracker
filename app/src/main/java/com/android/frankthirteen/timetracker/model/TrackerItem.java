package com.android.frankthirteen.timetracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItem {
    private UUID mId;
    private Boolean mIsStarted = false;
    private Date defaultDate;
    private int mDuration = 0;
    private String mTitle, mContent, mCommit;
    private ArrayList<DurationItem> mDurationItems;
    private Photo mPhoto = null;

    public TrackerItem() {
        mId = UUID.randomUUID();
        defaultDate = new Date();
        //Test constructor
        mTitle = "Hello";
        mContent = "Tracker";
        mDurationItems = new ArrayList<DurationItem>();
    }


    public Boolean isStarted() {
        return mIsStarted;
    }

    public void setmIsStarted(Boolean b) {
        mIsStarted = b;
//        Log.d("Tracker Item", this + "start set");
    }

    public UUID getmId() {
        return mId;
    }

    public int getmDuration() {
        return mDuration;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void increase() {
        mDuration++;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmCommit(String mCommit) {
        this.mCommit = mCommit;
    }

    public String getmCommit() {
        return mCommit;
    }

    public void saveDuration() {
        //used when item pause;
        DurationItem durationItem = new DurationItem(new Date(), mDuration, mId);
        mDurationItems.add(durationItem);
    }

    public ArrayList<DurationItem> getmDurationItems() {
        return mDurationItems;
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

    public void setmPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }
}
