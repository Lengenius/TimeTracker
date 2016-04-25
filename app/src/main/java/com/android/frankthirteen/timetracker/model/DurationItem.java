package com.android.frankthirteen.timetracker.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 4/21/16.
 */
public class DurationItem {
    private UUID trackId;
    private Date mEndDate;
    private int mDuration;

    DurationItem(Date date, int duration, UUID id){
        mEndDate = date;
        mDuration = duration;
        trackId = id;
    }


    public void setmEndDate(Date endDate){
        mEndDate = endDate;
    }

    public void setmDuration(int duration){
        mDuration = duration;
    }

    /**@return
     * the get method will be used when reporting.
     */

    public UUID getTrackId() {
        return trackId;
    }
    public Date getmEndDate() {
        return mEndDate;
    }

    public int getmDuration() {
        return mDuration;
    }
}
