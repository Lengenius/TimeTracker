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

    private static final String TABLE_TRACKER = "tracker_item";
    private static final String TABLE_DURATION = "tracker_duration";

    private static final String ITEM_START_DATE = "startDate";
    private static final String ITEM_END_DATE = "endDate";
    private static final String ITEM_TRACKING_ID = "trackingId";
    private static final String ITEM_TITLE = "title";
    private static final String ITEM_CONTENT = "content";
    private static final String ITEM_TOTAL_DURATION = "totalDuration";
    private static final String ITEM_TRACKING_STATE = "trackingState";
    private static final String ITEM_PHOTO_NAME = "photoName";

    private static final String DURATION_TRACKING_ID = "itemTrackingId";
    private static final String DURATION_YEAR = "year";
    private static final String DURATION_MONTH_OF_YEAR = "monthOfYear";
    private static final String DURATION_WEEK_OF_YEAR = "weekOfYear";
    private static final String DURATION_DAY = "day";
    private static final String DURATION_END_DATE = "endDate";
    private static final String DURATION_DAY_OF_MONTH = "dayOfMonth";
    private static final String DURATION_DURATION = "duration";

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

        if (!c.moveToFirst()) {
            c.close();
            ContentValues values = new ContentValues();
            values.put(ITEM_TRACKING_ID, trackerItem.getmId().toString());
            values.put(ITEM_TITLE, trackerItem.getmTitle());
            values.put(ITEM_CONTENT, trackerItem.getmContent());
            values.put(ITEM_TOTAL_DURATION, trackerItem.getmDuration());
            values.put(ITEM_START_DATE, trackerItem.getStartDate().getTime());
            values.put(ITEM_END_DATE, trackerItem.getEndDate().getTime());
            values.put(ITEM_TRACKING_STATE, trackerItem.getTrackingState());
            Log.d(TAG, String.valueOf(values));
            dbWrite.insert("tracker_item", null, values);
            Cursor cursor = dbWrite.query("tracker_item",null,null,null,null,null,null);
            Log.d(TAG,"After inserting there are " + cursor.getCount() + " items in table tracker_item.");
            cursor.close();

        }
    }

    /**
     * Save duration item into table which saves durations.
     *
     * @param durationItem tracking some specific duration in an activity.
     */
    public void saveDurationItem(DurationItem durationItem) {
        if (durationItem != null) {
            ContentValues values = new ContentValues();
            values.put(DURATION_TRACKING_ID , durationItem.getTrackId().toString());
            Log.d(TAG, durationItem.getTrackId().toString());
            values.put(DURATION_DURATION, durationItem.getmDuration());
            values.put(DURATION_END_DATE, durationItem.getmEndDate().getTime());
            values.put(DURATION_YEAR, durationItem.getYear());
            values.put(DURATION_MONTH_OF_YEAR, durationItem.getMonthOfYear());
            values.put(DURATION_WEEK_OF_YEAR, durationItem.getWeekOfYear());
            values.put(DURATION_DAY, durationItem.getDay());
            values.put(DURATION_DAY_OF_MONTH, durationItem.getDayOfMonth());
            Log.d(TAG, String.valueOf(values));
            dbWrite.insert(TABLE_DURATION, null, values);
            Cursor cursor = dbWrite.query(TABLE_DURATION,null,null,null,null,null,null);
            Log.d(TAG,"After inserting there are " + cursor.getCount() + " items in table duration.");
            cursor.close();
        }
    }



    public List<DurationItem> loadDuration(TrackerItem trackerItem) {
        Log.d(TAG, "loading Duration");
        List<DurationItem> durationItems = trackerItem.getmDurationItems();
        Cursor cursor = dbRead.query(TABLE_DURATION, null,
                DURATION_TRACKING_ID + "=?",
                new String[]{trackerItem.getmId().toString()},
                null, null, null);
        Log.d(TAG, "There are " + cursor.getCount() + " items.");
        if (cursor.moveToFirst()) {
            do {
                Date durationEndDate = new Date(cursor.getLong(cursor.getColumnIndex(DURATION_END_DATE)));
                int duration = cursor.getInt(cursor.getColumnIndex(DURATION_DURATION));
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
        Cursor cursor = dbRead.query(TABLE_TRACKER, null, null, null, null, null, null);
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
