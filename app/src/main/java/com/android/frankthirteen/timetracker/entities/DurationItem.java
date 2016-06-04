package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 */
public class DurationItem {
    private Context mContext;
    private String mTitle;
    private String mContent;
    private Date mDate;
    private int mDuration;
    private UUID mId;
    private UUID trackerId;

    public DurationItem(){

    }

    public DurationItem(Context context) {
        mContext = context;
        mDate = new Date();
        mId = UUID.randomUUID();
        mDuration = 0;
    }

    public UUID getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(UUID trackerId) {
        this.trackerId = trackerId;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getmDate() {
        return mDate;
    }

    /**
     * Set Item start and end time;
     *
     * @param mDate always is the start time.
     */
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
