package com.android.frankthirteen.timetracker.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 4/21/16.
 */
public class DurationItem {
    private UUID trackingId;
    private Date mEndDate;
    private int year, monthOfYear, weekOfYear, day, dayOfMonth;
    private int mDuration;

    public DurationItem(Date date, int duration, UUID id) {
        mEndDate = date;
        mDuration = duration;
        trackingId = id;
        devideDate();
    }


    public void setmEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public void setmDuration(int duration) {
        mDuration = duration;
    }

    /**
     * @return the get method will be used when reporting.
     */

    public UUID getTrackId() {
        return trackingId;
    }

    public Date getmEndDate() {
        return mEndDate;
    }

    public int getmDuration() {
        return mDuration;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getDay() {
        return day;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    /**
     * Break down the end Date, so we can report easily.
     */
    private void devideDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(mEndDate);
        year = c.get(Calendar.YEAR);
        monthOfYear = c.get(Calendar.MONTH);
        weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        day = c.get(Calendar.DAY_OF_YEAR);
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    }
}
