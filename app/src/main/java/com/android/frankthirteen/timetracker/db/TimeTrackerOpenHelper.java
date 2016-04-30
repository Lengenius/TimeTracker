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
            + "photoPath text,"
            + "trackingState integer,"
            + "photoName text)";

    private static final String CREATE_TABLE_DURATION = "create table tracker_duration("
            + "id integer primary key autoincrement,"
            + "year integer,"
            + "monthOfYear integer,"
            + "weekOfYear integer,"
            + "day integer,"
            + "dayOfMonth integer,"
            + "itemTrackingId text,"
//            + "references tracker_item(trackingId),"
            + "duration integer,"
            + "endDate integer)";

//    private static final String CREATE_TABLE_DURATION = "create table tracker_duration("
//            + "id integer primary key autoincrement,"
//            + "tracking_id integer,"
//            + "endDate integer,"
//            + "year integer,"
//            + "monthOfYear integer,"
//            + "weekOfYear integer,"
//            + "day integer,"
//            + "dayOfMonth integer)";

    private Context mContext;

    public TimeTrackerOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRACKER_ITEM);
        Log.d(TAG, "table tracker create");
        db.execSQL(CREATE_TABLE_DURATION);
        Log.d(TAG, "table duration create");
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
