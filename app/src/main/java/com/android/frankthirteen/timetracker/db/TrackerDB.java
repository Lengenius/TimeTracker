package com.android.frankthirteen.timetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 5/26/16.
 */
public class TrackerDB {

    private static final String TAG = "DB";
    /**
     * make the column name constant to avoid misname error.
     */
    public static final String TABLE_DURATION = "duration_item";
    public static final String DURATION_UID = "duration_id";
    public static final String DURATION_TRACKER_ID = "tracker_id";
    public static final String DURATION_PERIOD = "period";
    public static final String DURATION_TAG = "tag";
    public static final String DURATION_COMMENT = "comment";
    public static final String DURATION_DATE = "start_date";
    public static final String DURATION_DAY = "day";
    public static final String DURATION_WEEK = "week";
    public static final String DURATION_MONTH = "month";
    public static final String DURATION_YEAR = "year";


    public static final String TABLE_TRACKER = "tracker";
    public static final String TRACKER_ID = "tracker_id";
    public static final String TRACKER_TITLE = "title";
    public static final String TRACKER_CONTENT = "content";
    public static final String TRACKER_COMMENT = "comment";
    public static final String TRACKER_PHOTO_PATH = "photo_path";
    public static final String TRACKER_TOTAL_DURATION = "duration";
    public static final String TRACKER_PLANED_TIME = "planned_time_in_minutes";
    public static final String TRACKER_TRACKING_STATE = "tracking_state";
    public static final String TRACKER_START_DATE = "start_date";
    public static final String TRACKER_END_DATE = "end_date";

    public static final String DB_NAME = "TimeTrackerDB";
    public static final int DB_VERSION = 1;
    private static TrackerDB trackerDB;
    private SQLiteDatabase db;
    private Context mContext;

    private TrackerDB(Context context) {
        TrackerDBHelper trackerDBHelper = new TrackerDBHelper(context, DB_NAME, null, DB_VERSION);
        db = trackerDBHelper.getWritableDatabase();
        mContext = context;
    }

    public synchronized static TrackerDB getTrackerDB(Context context) {
        if (trackerDB == null) {
            trackerDB = new TrackerDB(context);
        }
        return trackerDB;
    }

    /**
     * encapsulate some method for other user.
     */
    public void insertTracker(Tracker tracker) {
        LogUtils.d(TAG, "inserting invoked.");
        if (tracker != null) {
            ContentValues values = assemblyTrackerValue(tracker);
            LogUtils.d(TAG, values.toString());
            db.insert(TABLE_TRACKER, null, values);
        }
    }


    public List<Tracker> getTrackers() {
        List<Tracker> trackers = new ArrayList<Tracker>();
        Cursor c = db.query(TABLE_TRACKER, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Tracker tracker = new Tracker();
                tracker.setId(UUID.fromString(c.getString(c.getColumnIndex(TRACKER_ID))));
                tracker.setTitle(c.getString(c.getColumnIndex(TRACKER_TITLE)));
                tracker.setContent(c.getString(c.getColumnIndex(TRACKER_CONTENT)));
                tracker.setComment(c.getString(c.getColumnIndex(TRACKER_COMMENT)));
                tracker.setTracking(c.getInt(c.getColumnIndex(TRACKER_TRACKING_STATE)) > 0);
                tracker.setPlanningTimeInMinutes(c.getInt(c.getColumnIndex(TRACKER_PLANED_TIME)));
                if (c.getLong(c.getColumnIndex(TRACKER_START_DATE)) != 0) {
                    Date date = new Date();
                    date.setTime(c.getLong(c.getColumnIndex(TRACKER_START_DATE)));
                    tracker.setStartDate(date);
                }
                if (c.getLong(c.getColumnIndex(TRACKER_END_DATE)) != 0) {
                    Date date = new Date();
                    date.setTime(c.getLong(c.getColumnIndex(TRACKER_END_DATE)));
                    tracker.setEndDate(date);
                }
                if (c.getString(c.getColumnIndex(TRACKER_PHOTO_PATH)) != null) {
                    tracker.setPhotoPath(c.getString(c.getColumnIndex(TRACKER_PHOTO_PATH)));
                }


                trackers.add(tracker);
            }
            while (c.moveToNext());
            c.close();
            return trackers;
        }
        return trackers;
    }

    public boolean removeTracker(Tracker tracker) {
        String trackerId = tracker.getId().toString();
        Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?", new String[]{trackerId},
                null, null, null);
        db.delete(TABLE_TRACKER, TRACKER_ID + "=?", new String[]{trackerId});
        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        return true;
    }

    public void modifyTracker(Tracker tracker) {
        Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?",
                new String[]{tracker.getId().toString()}, null, null, null);
        if (cursor.moveToFirst()) {
            ContentValues values = assemblyTrackerValue(tracker);

            db.update(TABLE_TRACKER, values, TRACKER_ID + "=?",
                    new String[]{tracker.getId().toString()});
            cursor.close();
        }
    }
/**
 public void updateTrackerDuration(Tracker tracker) {
 Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?",
 new String[]{tracker.getId().toString()}, null, null, null);
 if (cursor.moveToFirst()) {
 ContentValues contentValues = new ContentValues();
 contentValues.put(TRACKER_TOTAL_DURATION, tracker.getTotalDurations());
 db.update(TABLE_TRACKER, contentValues, TRACKER_ID + "=?",
 new String[]{tracker.getId().toString()});
 cursor.close();
 }
 }
 */

    /**
     * Duration part
     */

    public void insertDurationItem(DurationItem di) {
        if (di != null) {
            ContentValues contentValues = assemblyDurationValues(di);
            LogUtils.d(TAG, contentValues.toString());
            db.insert(TABLE_DURATION, null, contentValues);
        }
    }


    /**
     * public boolean updateDurationItem(DurationItem di) {
     * if (di != null) {
     * ContentValues contentValues = new ContentValues();
     * try {
     * contentValues.put(DURATION_UID, di.getId().toString());
     * contentValues.put(DURATION_TRACKER_ID, di.getTrackerId().toString());
     * contentValues.put(DURATION_PERIOD, di.getDuration());
     * contentValues.put(DURATION_DATE, di.getDate().getTime());
     * contentValues.put(DURATION_COMMENT, di.getComment());
     * db.update(TABLE_DURATION, contentValues, DURATION_UID + "=?", new String[]{di.getId().toString()});
     * return true;
     * } catch (Exception e) {
     * e.printStackTrace();
     * return false;
     * }
     * }
     * return false;
     * }
     */

    public List<DurationItem> getDurationItemsByTracker(Tracker tracker) {
        List<DurationItem> durationItems = new ArrayList<DurationItem>();
        String trackerId = tracker.getId().toString();
        Cursor c = db.query(TABLE_DURATION, null,
                DURATION_TRACKER_ID + "=?",
                new String[]{trackerId}, null, null, null);
        if (c.moveToFirst()) {
            do {
                DurationItem di = assemblyDurationItem(c);
                di.setTrackerId(UUID.fromString(trackerId));
                durationItems.add(di);
            } while (c.moveToNext());
            c.close();
            return durationItems;
        }
        c.close();
        return durationItems;
    }

    public List<DurationItem> getDurationItemsByDay(int year, int day) {
        List<DurationItem> durationItems = new ArrayList<DurationItem>();
        String dayStr = String.valueOf(day);
        Cursor c = db.query(TABLE_DURATION, null, DURATION_DAY + "=?",
                new String[]{dayStr}, null, null, null);
        if (c.moveToFirst()) {
            do {
                DurationItem di = assemblyDurationItem(c);
                di.setTrackerId(
                        UUID.fromString(c.getString(c.getColumnIndex(DURATION_TRACKER_ID))));
                durationItems.add(di);
            } while (c.moveToNext());
            c.close();
        }
        //get durations by week month day or year.
        LogUtils.d(TAG, "There are items:" + durationItems.size());
        return durationItems;
    }

    @NonNull
    private ContentValues assemblyDurationValues(DurationItem di) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DURATION_UID, di.getId().toString());
        if (di.getTrackerId() != null) {
            LogUtils.d("DBDuration", "TrackerId is :" + di.getTrackerId().toString());
            contentValues.put(DURATION_TRACKER_ID, di.getTrackerId().toString());
        }
        contentValues.put(DURATION_PERIOD, di.getDuration());
        contentValues.put(DURATION_DATE, di.getDate().getTime());
        contentValues.put(DURATION_COMMENT, di.getComment());
        contentValues.put(DURATION_TAG, di.getTag());
        LogUtils.d(TAG, "Duration item tag is " + di.getTag());
        contentValues.put(DURATION_YEAR, di.getYear());
        contentValues.put(DURATION_MONTH, di.getMonth());
        contentValues.put(DURATION_WEEK, di.getWeek());
        contentValues.put(DURATION_DAY, di.getDay());
        return contentValues;
    }

    @NonNull
    private ContentValues assemblyTrackerValue(Tracker tracker) {
        ContentValues values = new ContentValues();
        values.put(TRACKER_ID, tracker.getId().toString());
        values.put(TRACKER_TITLE, tracker.getTitle());
        values.put(TRACKER_CONTENT, tracker.getContent());
        values.put(TRACKER_COMMENT, tracker.getComment());
        values.put(TRACKER_TRACKING_STATE, tracker.isTracking());
        values.put(TRACKER_PLANED_TIME, tracker.getPlannedTimeInMinutes());
        if (tracker.getStartDate() != null) {
            values.put(TRACKER_START_DATE, tracker.getStartDate().getTime());
        }
        if (tracker.getEndDate() != null) {
            values.put(TRACKER_END_DATE, tracker.getEndDate().getTime());
        }
        if (tracker.getPhotoPath() != null) {
            values.put(TRACKER_PHOTO_PATH, tracker.getPhotoPath());
        }
        return values;
    }

    @NonNull
    private DurationItem assemblyDurationItem(Cursor c) {
        DurationItem di = new DurationItem(mContext);
        di.setId(UUID.fromString(c.getString(c.getColumnIndex(DURATION_UID))));
        Date diDate = new Date();
        diDate.setTime(c.getLong(c.getColumnIndex(DURATION_DATE)));
        di.setDate(diDate);
        di.setComment(c.getString(c.getColumnIndex(DURATION_COMMENT)));
        di.setDuration(c.getInt(c.getColumnIndex(DURATION_PERIOD)));
        di.setTag(c.getString(c.getColumnIndex(DURATION_TAG)));
        return di;
    }
}
