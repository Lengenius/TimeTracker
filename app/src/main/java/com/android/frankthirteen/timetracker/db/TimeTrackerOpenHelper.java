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
        db.execSQL(CREATE_TIMER_SAVED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                db.execSQL(CREATE_TIMER_SAVED);
            default:
        }
    }

    Date date = new Date();

    public static final String CREATE_TIMER_SAVED = "create table timer("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "content text,"
            + "idImg integer,"
            + "starttime integer)";

}
