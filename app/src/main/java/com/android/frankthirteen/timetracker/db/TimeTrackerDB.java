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
import com.android.frankthirteen.timetracker.utils.LogUtils;

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
    private static final String ITEM_PHOTO_PATH = "photoPath";

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
        LogUtils.d(TAG, "saving item");
        Cursor c = dbRead.query(TABLE_TRACKER, new String[]{"trackingId"}, "trackingId=?",
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
            if (trackerItem.getmPhoto() != null) {
                values.put(ITEM_PHOTO_PATH, trackerItem.getmPhoto().getmPhotoPath());
            }
            values.put(ITEM_TRACKING_STATE, trackerItem.getTrackingState());
            LogUtils.d(TAG,"saving item " + values.toString());
            dbWrite.insert(TABLE_TRACKER, null, values);
            Cursor cursor = dbWrite.query("tracker_item", null, null, null, null, null, null);
            cursor.close();
        } else {
            c.close();
            updateTrackerItem(trackerItem);

        }
    }

    public void updateTrackerItem(TrackerItem trackerItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_TRACKING_ID, trackerItem.getmId().toString());
        contentValues.put(ITEM_TITLE, trackerItem.getmTitle());
        contentValues.put(ITEM_CONTENT, trackerItem.getmContent());
        contentValues.put(ITEM_TOTAL_DURATION, trackerItem.getmDuration());
        if (trackerItem.getmPhoto()!=null) {
            contentValues.put(ITEM_PHOTO_PATH, trackerItem.getmPhoto().getmPhotoPath());
        }
        contentValues.put(ITEM_START_DATE, trackerItem.getStartDate().getTime());
        contentValues.put(ITEM_END_DATE, trackerItem.getEndDate().getTime());
        dbWrite.update(TABLE_TRACKER, contentValues, ITEM_TRACKING_ID + "=?", new String[]{trackerItem.getmId().toString()});

    }

    /**
     * Save duration item into table which saves durations.
     *
     * @param durationItem tracking some specific duration in an activity.
     */
    public void saveDurationItem(DurationItem durationItem) {
        if (durationItem != null) {
            ContentValues values = new ContentValues();
            values.put(DURATION_TRACKING_ID, durationItem.getTrackId().toString());
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
        }
    }


    public List<DurationItem> loadDuration(TrackerItem trackerItem) {
        List<DurationItem> durationItems = trackerItem.getmDurationItems();
        Cursor cursor = dbRead.query(TABLE_DURATION, null,
                DURATION_TRACKING_ID + "=?",
                new String[]{trackerItem.getmId().toString()},
                null, null, null);
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
    public ArrayList<TrackerItem> loadTrackerItem() {
        ArrayList<TrackerItem> trackerItemList = new ArrayList<TrackerItem>();
        Cursor cursor = dbRead.query(TABLE_TRACKER, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TrackerItem trackerItem = new TrackerItem(mContext);
                trackerItem.setmId(UUID.fromString(cursor.getString(cursor.getColumnIndex(ITEM_TRACKING_ID))));
                List<DurationItem> durationItems;
                durationItems = loadDuration(trackerItem);
                trackerItem.setmDurationItems(durationItems);
                if (cursor.getString(cursor.getColumnIndex(ITEM_PHOTO_PATH)) != null) {
                    String photoPath = cursor.getString(cursor.getColumnIndex(ITEM_PHOTO_PATH));
                    trackerItem.setmPhoto(new Photo(trackerItem.getmId(), photoPath));
                }
                trackerItem.setmTitle(cursor.getString(cursor.getColumnIndex(ITEM_TITLE)));
                trackerItem.setmContent(cursor.getString(cursor.getColumnIndex(ITEM_CONTENT)));
                trackerItem.setTrackingState(cursor.getInt(cursor.getColumnIndex(ITEM_TRACKING_STATE)) != 0);

                trackerItemList.add(trackerItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trackerItemList;
    }

    public boolean deleteTrackerItem(TrackerItem item){
        Cursor cursor = dbWrite.query(TABLE_TRACKER,null,ITEM_TRACKING_ID+"=?",new String[]{item.getmId().toString()},null,null,null);
        if (cursor.moveToFirst()){
            dbWrite.delete(TABLE_TRACKER,ITEM_TRACKING_ID+"=?",new String[]{item.getmId().toString()});
            dbWrite.delete(TABLE_DURATION,DURATION_TRACKING_ID+"=?",new String[]{item.getmId().toString()});
            return true;
        }else {
            return false;
        }
    }


}
