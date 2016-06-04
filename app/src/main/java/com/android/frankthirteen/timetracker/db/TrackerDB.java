package com.android.frankthirteen.timetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 5/26/16.
 */
public class TrackerDB {
    /**
     * make the column name constant to avoid misname error.
     */
    public static final String TABLE_DURATION = "duration_item";
    public static final String DURATION_UID = "uid";
    public static final String DURATION_DURATION = "duration";
    public static final String DURATION_CONTENT = "content";
    public static final String DURATION_TRACKER_ID = "tracker_id";
    public static final String DURATION_DATE = "date";

    public static final String TABLE_TRACKER = "tracker";
    public static final String TRACKER_ID = "uid";
    public static final String TRACKER_TITLE = "title";
    public static final String TRACKER_GOAL = "goal";
    public static final String TRACKER_CONTENT = "content";
    public static final String TRACKER_TAG = "tag";
    public static final String TRACKER_STATE = "tracking_state";
    public static final String TRACKER_TOTAL_DURATION = "total_duration";

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
        if (tracker != null) {
            ContentValues values = new ContentValues();
            values.put(TRACKER_ID, tracker.getId().toString());
            values.put(TRACKER_CONTENT, tracker.getContent());
            values.put(TRACKER_GOAL, tracker.getGoal());
            values.put(TRACKER_TITLE, tracker.getTitle());
            values.put(TRACKER_TAG, tracker.getmTag());
            values.put(TRACKER_STATE, tracker.isTracking());
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
                tracker.setmTag(c.getString(c.getColumnIndex(TRACKER_TAG)));
                tracker.setGoal(c.getString(c.getColumnIndex(TRACKER_GOAL)));
                tracker.setTracking(c.getInt(c.getColumnIndex(TRACKER_STATE))>0);

                trackers.add(tracker);
            }
            while (c.moveToNext());
            c.close();
            return trackers;
        }
        return trackers;
    }

    public void updateTracker(Tracker tracker) {
        Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?",
                new String[]{tracker.getId().toString()}, null, null, null);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(TRACKER_ID, tracker.getId().toString());
            values.put(TRACKER_CONTENT, tracker.getContent());
            values.put(TRACKER_GOAL, tracker.getGoal());
            values.put(TRACKER_TITLE, tracker.getTitle());
            values.put(TRACKER_TAG, tracker.getmTag());
            db.update(TABLE_TRACKER, values,
                    TRACKER_ID + "=?",
                    new String[]{tracker.getId().toString()});
            cursor.close();
        }
    }

    public void updateTrackerDuration(Tracker tracker){
        Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?",
                new String[]{tracker.getId().toString()}, null, null, null);
        if (cursor.moveToFirst()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRACKER_TOTAL_DURATION,tracker.getTotalDurations());
            db.update(TABLE_TRACKER,contentValues,TRACKER_ID+"=?",
                    new String[]{tracker.getId().toString()});
            cursor.close();
        }
    }

    public void addTrackerPhoto(Tracker tracker) {
        Cursor cursor = db.query(TABLE_TRACKER, null, TRACKER_ID + "=?",
                new String[]{tracker.getId().toString()}, null, null, null);

        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(TRACKER_TAG, tracker.getmTag());
            db.update(TABLE_TRACKER, values,
                    TRACKER_ID + "=?",
                    new String[]{tracker.getId().toString()});
            cursor.close();
        }
    }

    /**
     * Duration part
     */

    public void insertDurationItem(DurationItem di) {
        if (di != null) {
            ContentValues contentValues = new ContentValues();
            try {
                contentValues.put(DURATION_UID, di.getmId().toString());
                contentValues.put(DURATION_TRACKER_ID, di.getTrackerId().toString());
                contentValues.put(DURATION_DURATION, di.getmDuration());
                contentValues.put(DURATION_DATE, di.getmDate().getTime());
                contentValues.put(DURATION_CONTENT, di.getmContent());
                db.insert(TABLE_DURATION, null, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateDurationItem(DurationItem di) {
        if (di != null) {
            ContentValues contentValues = new ContentValues();
            try {
                contentValues.put(DURATION_UID, di.getmId().toString());
                contentValues.put(DURATION_TRACKER_ID, di.getTrackerId().toString());
                contentValues.put(DURATION_DURATION, di.getmDuration());
                contentValues.put(DURATION_DATE, di.getmDate().getTime());
                contentValues.put(DURATION_CONTENT, di.getmContent());
                db.update(TABLE_DURATION, contentValues, DURATION_UID + "=?", new String[]{di.getmId().toString()});
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public List<DurationItem> getDurationItemsByTracker(Tracker tracker) {
        List<DurationItem> durationItems = new ArrayList<DurationItem>();
        String trackerId = tracker.getId().toString();
        Cursor c = db.query(TABLE_DURATION, null, DURATION_TRACKER_ID + "=?",
                new String[]{trackerId}, null, null, null);
        if (c.moveToFirst()) {
            do {
                DurationItem di = new DurationItem();
                di.setTrackerId(UUID.fromString(trackerId));
                di.setmId(UUID.fromString(c.getString(c.getColumnIndex(DURATION_UID))));
                Date diDate = new Date();
                diDate.setTime(c.getLong(c.getColumnIndex(DURATION_DATE)));
                di.setmDate(diDate);
                di.setmContent(c.getString(c.getColumnIndex(DURATION_CONTENT)));
                di.setmDuration(c.getInt(c.getColumnIndex(DURATION_DURATION)));
                durationItems.add(di);
            } while (c.moveToNext());
            c.close();
            return durationItems;
        }
        return durationItems;
    }


}
