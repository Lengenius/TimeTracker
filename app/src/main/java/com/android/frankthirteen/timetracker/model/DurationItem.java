package com.android.frankthirteen.timetracker.model;

import java.util.Date;

/**
 * Created by Frank on 4/21/16.
 */
public class DurationItem {
    private Date mEndDate;
    private int mDuration;

    DurationItem(Date date, int duration){
        mEndDate = date;
        mDuration = duration;
    }

    public void setmEndDate(Date endDate){
        mEndDate = endDate;
    }

    public void setmDuration(int duration){
        mDuration = duration;
    }

    /**@return
     * the two get method will be used when reporting.
     */

    public Date getmEndDate() {
        return mEndDate;
    }

    public int getmDuration() {
        return mDuration;
    }
}
