package com.android.frankthirteen.timetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Frank on 5/24/16.
 */
public class TrackerDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_DURATION_ITEM = "create table duration_item(" +
            "_id integer primary key autoincrement," +
            "uid text," +
            "duration integer," +
            "title text," +
            "content text," +
            "tracker_id text" +
            "date integer," +
            "tag text)";

    private static final String CREATE_TABLE_TRACKER = "create table tracker("+
            "_id integer primary key autoincrement,"+
            "tracker_id text," +
            "tracker_title text," +
            "tracker_goal text," +
            "tracker_content text)";


    public TrackerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
