package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import com.android.frankthirteen.timetracker.R;

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
    private int mTag = 0x00ff;
    private String mComment;
    private Date mEndDate;

    public static final String EXTRA_DI_ID =
            "com.android.frankthirteen.timetracker.entities.durationId";

    /**
     * Default Constructor for DB only.
     */
    public DurationItem() {
    }

    public DurationItem(Context context) {
        mContext = context;
        mId = UUID.randomUUID();
        mEndDate = new Date();

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
        return mEndDate;
    }

    public int getYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(mEndDate);
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(mEndDate);
        return c.get(Calendar.MONTH);
    }

    public int getWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(mEndDate);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(mEndDate);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Set Item start time;
     *
     * @param mDate always is the start time.
     */
    public void setDate(Date mDate) {
        this.mEndDate = mDate;
    }

    public int getDuration() {
        return mPeriod;
    }

    public void setDuration(int mDuration) {
        this.mPeriod = mDuration;
    }

    public int getTag() {
        return mTag;
    }

    public void setTag(int mTag) {
        this.mTag = mTag;
    }

    public Date getStartDate() {
        Date mStartDate = new Date();
        mStartDate.setTime(mEndDate.getTime() - mPeriod * 1000);
        return mStartDate;
    }

    public Tracker getTracker() {
        if (trackerId!=null){
         return TrackerLab.getTrackerLab(mContext).getTracker(trackerId);
        }else {
            return null;
        }
    }

    public String getTagValue() {
        switch (mTag) {

            case 0:
                return mContext.getString(R.string.tag_sport);
            case 1:
                return mContext.getString(R.string.tag_Entertainment);
            case 2:
                return mContext.getString(R.string.tag_work);
            case 3:
                return mContext.getString(R.string.tag_traffic);
            case 4:
                return mContext.getString(R.string.tag_study);
            case 5:
                return mContext.getString(R.string.tag_hobby);
            case 6:
                return mContext.getString(R.string.tag_rest);
            default:
                return null;
        }

    }

}
