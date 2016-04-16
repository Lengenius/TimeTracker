package com.android.frankthirteen.timetracker.utils;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItem {
    private UUID mId;
    private Date mDate;
    private Boolean mIsStarted = false;
    private int mDuration = 0;
    private String mTitle, mContent;

    public TrackerItem() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mTitle = "Hello";
        mContent = "Tracker";
    }

    public TrackerItem(Date date) {
        mId = UUID.randomUUID();
        mDate = date;
    }

    public Date getmDate() {
        return mDate;
    }

    public Boolean isStarted() {
        return mIsStarted;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public void setmIsStarted(Boolean b) {
        mIsStarted = b;
        Log.d("Tracker Item", this + "start set");
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
}
