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
    private int mDuration = 0;
    private String mTitle, mContent;
    private ArrayList<DurationItem> mDurationItems;

    public TrackerItem() {
        mId = UUID.randomUUID();
        //Test constructor
        mTitle = "Hello";
        mContent = "Tracker";
        mDurationItems = new ArrayList<DurationItem>();
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

    public void saveDuration(){
        //used when item pause;
        DurationItem durationItem = new DurationItem(new Date(),mDuration);
        mDurationItems.add(durationItem);
    }

    public ArrayList<DurationItem> getmDurationItems() {
        return mDurationItems;
    }

    public Date getStartDate(){
        DurationItem mDurationItem = mDurationItems.get(0);
        Date endDate = mDurationItem.getmEndDate();
        int duration = mDurationItem.getmDuration();
        return new Date(endDate.getTime() + duration);
    }

    public Date getEndDate(){

        return mDurationItems.get(mDurationItems.size()).getmEndDate();
    }
}
