package com.android.frankthirteen.timetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.frankthirteen.timetracker.model.DurationItem;
import com.android.frankthirteen.timetracker.model.TrackerItem;

/**
 * Created by Frank on 4/25/16.
 */
public class TimeTrackerDB {
    public static final String DB_NAME = "timer_tracker";
    public static int VERSION = 1;

    private static TimeTrackerDB timeTrackerDB;
    private SQLiteDatabase db;

    private TimeTrackerDB(Context context) {
        TimeTrackerOpenHelper dbOpenHelper = new TimeTrackerOpenHelper(context, DB_NAME, null, VERSION);
        db = dbOpenHelper.getWritableDatabase();
    }

    public synchronized static TimeTrackerDB getInstance(Context context) {
        if (timeTrackerDB == null) {
            timeTrackerDB = new TimeTrackerDB(context);
        }
        return timeTrackerDB;
    }

    public void saveTrackerItem(TrackerItem trackerItem) {
        if (trackerItem != null) {
            ContentValues values = new ContentValues();
            values.put("uuid", trackerItem.getmId().toString());
            values.put("title", trackerItem.getmTitle());
            values.put("content", trackerItem.getmContent());
            values.put("commit", trackerItem.getmCommit());
            values.put("totalDuration", trackerItem.getmDuration());
            db.insert("tracker_item", null, values);
        }
    }

    public void saveDurationItem(DurationItem durationItem){
        if (durationItem!=null){
            ContentValues values = new ContentValues();
            values.put("UUID", durationItem.getTrackId().toString());
            values.put("duration",durationItem.getmDuration());
            values.put("endDate", durationItem.getmEndDate().getTime());
        }
    }

}
