package com.android.frankthirteen.timetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import com.android.frankthirteen.timetracker.model.DurationItem;
import com.android.frankthirteen.timetracker.model.Photo;
import com.android.frankthirteen.timetracker.model.TrackerItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 4/25/16.
 */
public class TimeTrackerDB {
    public static final String DB_NAME = "TimeTracker";
    public static int VERSION = 1;


    private static TimeTrackerDB timeTrackerDB;
    private Context mContext;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;
    private static final String TAG = "TTDB";

    private TimeTrackerDB(Context context) {
        mContext = context;
        TimeTrackerOpenHelper dbOpenHelper = new TimeTrackerOpenHelper(context, DB_NAME, null, VERSION);
        dbWrite = dbOpenHelper.getWritableDatabase();
        dbRead = dbOpenHelper.getReadableDatabase();
    }

    public synchronized static TimeTrackerDB getInstance(Context context) {
        if (timeTrackerDB == null) {
            timeTrackerDB = new TimeTrackerDB(context.getApplicationContext());
        }
        return timeTrackerDB;
    }

    /**
     * Save tracker items into table tracker_item.
     *
     * @param trackerItem tracking some specific activity.
     */
    public void saveTrackerItem(TrackerItem trackerItem) {
        Cursor c = dbRead.query("tracker_item", new String[]{"trackingId"}, "trackingId=?",
                new String[]{trackerItem.getmId().toString()}, null, null, null);

        Log.d(TAG, "saving tracker " + String.valueOf(c.moveToFirst()));
//        Log.d(TAG, "there are " + c.getCount() + " items in tracker_item");
        if (!c.moveToFirst()) {
            c.close();
            ContentValues values = new ContentValues();
            values.put("trackingId", trackerItem.getmId().toString());
            values.put("title", trackerItem.getmTitle());
            values.put("content", trackerItem.getmContent());
            values.put("totalDuration", trackerItem.getmDuration());
            values.put("startDate", trackerItem.getStartDate().getTime());
            values.put("endDate", trackerItem.getEndDate().getTime());
            values.put("trackingState", trackerItem.getTrackingState());
            Log.d(TAG, String.valueOf(values));
            dbWrite.insert("tracker_item", null, values);
            Cursor cursor = dbWrite.query("tracker_item",null,null,null,null,null,null);
            Log.d(TAG,"After inserting there are " + cursor.getCount() + " items in table tracker_item.");

        }
    }

    /**
     * Save duration item into table which saves durations.
     *
     * @param durationItem tracking some specific duration in an activity.
     */
//    public void saveDurationItem(DurationItem durationItem) {
//        if (durationItem != null) {
//            ContentValues values = new ContentValues();
//            values.put("item_trackingId", durationItem.getTrackId().toString());
//            Log.d(TAG, durationItem.getTrackId().toString());
//            values.put("duration", durationItem.getmDuration());
//            values.put("endDate", durationItem.getmEndDate().getTime());
//            values.put("year", durationItem.getYear());
//            values.put("monthOfYear", durationItem.getMonthOfYear());
//            values.put("weekOfYear", durationItem.getWeekOfYear());
//            values.put("day", durationItem.getDay());
//            values.put("dayOfMonth", durationItem.getDayOfMonth());
//            Log.d(TAG, String.valueOf(values));
//            dbWrite.insert(TimeTrackerOpenHelper.TABLE_DURATION, null, values);
//            Cursor cursor = dbWrite.query(TimeTrackerOpenHelper.TABLE_DURATION,null,null,null,null,null,null);
//            Log.d(TAG,"After inserting there are " + cursor.getCount() + " items in table duration.");
//            cursor.close();
//        }
//    }

    public void saveDurationItem(DurationItem durationItem) {
        if (durationItem != null) {
            ContentValues values = new ContentValues();
            values.put("item_trackingId", durationItem.getTrackId().toString());
            Log.d(TAG, durationItem.getTrackId().toString());
            values.put("duration", 12);
            values.put("endDate", 9999999);
            values.put("year", 2016);
            values.put("monthOfYear", 3);
            values.put("weekOfYear", 5);
            values.put("day", 29);
            values.put("dayOfMonth", 29);
//            Log.d(TAG, String.valueOf(values));
            dbWrite.insert(TimeTrackerOpenHelper.TABLE_DURATION, null, values);
            Cursor cursor = dbWrite.query(TimeTrackerOpenHelper.TABLE_DURATION,null,null,null,null,null,null);
            Log.d(TAG,"After inserting there are " + cursor.getCount() + " items in table duration.");
            cursor.close();
        }
    }

    public List<DurationItem> loadDuration(TrackerItem trackerItem) {
        Log.d(TAG, "loading Duration");
        List<DurationItem> durationItems = trackerItem.getmDurationItems();
//        try {
        Cursor cursor = dbRead.query("tracker_duration", null,
                null,
                null,
//                TimeTrackerOpenHelper.DURATION_TRACKING_ID + "=?",
//                new String[]{trackerItem.getmId().toString()},
                null, null, null);
        Log.d(TAG, "There are " + cursor.getCount() + " items.");
        if (cursor.moveToFirst()) {
            do {
                Date durationEndDate = new Date(cursor.getLong(cursor.getColumnIndex("endDate")));
                Log.d(TAG, "" + cursor.getColumnIndex("duration"));
                Log.d(TAG, "duration item cursor" + cursor.toString());
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                Log.d(TAG, "" + duration);
                DurationItem durationItem = new DurationItem(durationEndDate, duration, trackerItem.getmId());
                durationItems.add(durationItem);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return durationItems;
    }

    /**
     * Load tracker items include finished and tracking items.
     */
    public List<TrackerItem> loadTrackerItem() {
        List<TrackerItem> trackerItemList = new ArrayList<TrackerItem>();
        Cursor cursor = dbRead.query(TimeTrackerOpenHelper.TABLE_TRACKER, null, null, null, null, null, null);
        Log.d(TAG, "loading Tracker Item");
        Log.d(TAG, String.valueOf(cursor.moveToFirst()));
        if (cursor.moveToFirst()) {
            do {
                Log.d(TAG, "new item start loading.");
                TrackerItem trackerItem = new TrackerItem(mContext);
                Log.d(TAG, "new trackerItem loading durationItems " + trackerItem.getmDurationItems());
                trackerItem.setmId(UUID.fromString(cursor.getString(cursor.getColumnIndex("trackingId"))));
                Log.d(TAG, "new trackerItem loading trackerItem " + trackerItem.getmId().toString());
                List<DurationItem> durationItems;
                durationItems = loadDuration(trackerItem);
                trackerItem.setmDurationItems(durationItems);
                trackerItem.setmTitle(cursor.getString(cursor.getColumnIndex("title")));
                trackerItem.setmContent(cursor.getString(cursor.getColumnIndex("content")));
                trackerItem.setTrackingState(cursor.getString(cursor.getColumnIndex("trackingState")));

                trackerItemList.add(trackerItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trackerItemList;

    }

}
