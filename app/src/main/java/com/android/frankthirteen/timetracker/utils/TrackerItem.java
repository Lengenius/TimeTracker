package com.android.frankthirteen.timetracker.utils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItem {
    private UUID mId;
    private Date mDate;
    private int mDuration;

    public TrackerItem(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public TrackerItem(Date date){
        mId = UUID.randomUUID();
        mDate = date;
    }

    public Date getmDate() {
        return mDate;
    }

    public UUID getmId() {
        return mId;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
