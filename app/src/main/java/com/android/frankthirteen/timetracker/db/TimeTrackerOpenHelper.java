package com.android.frankthirteen.timetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Frank on 2/28/16.
 */
public class TimeTrackerOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public TimeTrackerOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRACKER_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                db.execSQL(CREATE_TRACKER_ITEM);
            default:
        }
    }

    Date date = new Date();

    public static final String CREATE_TRACKER_ITEM = "create table tracker_item("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "content text,"
            + "idImage integer,"
            + "trueId integer)";

    public static final String CREATE_TRACKER_DURATION = "create table tracker_duration("
            + "id integer primary key autoincrement,"
            + "trueId integer,"
            + "dstart integer)";
}
