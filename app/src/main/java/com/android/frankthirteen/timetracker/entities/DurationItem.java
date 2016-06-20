package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 */
public class DurationItem {
    private Context mContext;
    private String mContent;
    private Date mDate;
    private int mDuration;
    private UUID mId;
    private UUID trackerId;
    private String mTag;

    /**
     * Default Constructor for DB only.
     */
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

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getDate() {
        return mDate;
    }

    /**
     * Set Item start and end time;
     *
     * @param mDate always is the start time.
     */
    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }
}
