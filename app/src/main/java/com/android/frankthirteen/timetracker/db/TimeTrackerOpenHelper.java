package com.android.frankthirteen.timetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Frank on 2/28/16.
 */
public class TimeTrackerOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "TTOpenHelper";

    private static final String CREATE_TRACKER_ITEM = "create table tracker_item("
            + "id integer primary key autoincrement,"
            + "startDate integer,"
            + "endDate integer,"
            + "trackingId text,"
            + "title text,"
            + "content text,"
            + "totalDuration integer,"
            + "trackingState blob,"
            + "photoName text)";
    public static final String TABLE_TRACKER = "tracker_item";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String TRACKING_ID = "trackingId";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TOTAL_DURATION = "totalDuration";
    public static final String TRACKING_STATE = "trackingState";
    public static final String PHOTO_NAME = "photoName";

    private static final String CREATE_TRACKER_DURATION = "create table tracker_duration("
            + "id integer primary key autoincrement,"
            + "year integer,"
            + "monthOfYear integer,"
            + "weekOfYear integer,"
            + "day integer,"
            + "dayOfMonth integer,"
            + "item_trackingId text references tracker_item(trackingId),"
            + "duration integer,"
            + "endDate integer)";
    public static final String DURATION_YEAR = "year";
    public static final String DURATION_MONTH_OF_YEAR = "monthOfYear";
    public static final String DURATION_WEEK_OF_YEAR = "weekOfYear";
    public static final String DURATION_DAY = "DAY";
    public static final String DURATION_DAY_OF_MONTH = "dayOfMonth";
    public static final String DURATION_DURATION = "duration";

    private Context mContext;

    public TimeTrackerOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRACKER_ITEM);
        Log.d(TAG,"table create");
        db.execSQL(CREATE_TRACKER_DURATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_TRACKER_ITEM);
            default:
        }
    }


}
