package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 5/24/16.
 */
public class DurationItem {
    private Context mContext;

    private UUID mId;
    private UUID trackerId;
    private int mPeriod;
    private String mTag;
    private String mComment;
    private Date mDate;

    /**
     * Default Constructor for DB only.
     */
    public DurationItem(){
    }

    public DurationItem(Context context) {
        mContext = context;
        mId = UUID.randomUUID();
        mDate = new Date();
        mPeriod = 0;
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

    public String getComment() {
        return mComment;
    }

    public void setComment(String mContent) {
        this.mComment = mContent;
    }

    public Date getDate() {
        return mDate;
    }

    public int getYear(){
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        return c.get(Calendar.YEAR);
    }

    public int getMonth(){
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        return c.get(Calendar.MONTH);
    }

    public int getDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Set Item start time;
     *
     * @param mDate always is the start time.
     */
    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getDuration() {
        return mPeriod;
    }

    public void setDuration(int mDuration) {
        this.mPeriod = mDuration;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }
}
