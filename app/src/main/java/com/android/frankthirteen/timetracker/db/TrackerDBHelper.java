package com.android.frankthirteen.timetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.frankthirteen.timetracker.utils.LogUtils;

/**
 * Created by Frank on 5/24/16.
 */
public class TrackerDBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_DURATION_ITEM = "create table duration_item(" +
            "_id integer primary key autoincrement," +
            "uid text," +
            "duration integer," +
            "content text," +
            "tracking_state int," +
            "tracker_id text" +
            "date integer)";

    private static final String CREATE_TABLE_TRACKER = "create table tracker(" +
            "_id integer primary key autoincrement," +
            "uid text," +
            "title text," +
            "goal text," +
            "total_duration integer," +
            "content text," +
            "tag text)";


    public TrackerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_DURATION_ITEM);
        db.execSQL(CREATE_TABLE_TRACKER);

        LogUtils.i("DBHelper", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
