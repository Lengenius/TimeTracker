package com.android.frankthirteen.timetracker.model;

import android.content.Context;
import android.util.Log;

import com.android.frankthirteen.timetracker.db.TimeTrackerDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItemLab {
    private static TrackerItemLab sTrackerItemLab;
    private static final String TAG = "TrackerItemLab";
    private Context mAppContext;

    private List<TrackerItem> trackerItems;

    private TrackerItemLab(Context appContext) {
        //Singleton
        mAppContext = appContext;
        TimeTrackerDB timeTrackerDB = TimeTrackerDB.getInstance(mAppContext);
        if (timeTrackerDB != null) {
            Log.d(TAG, "loading db");
            trackerItems = timeTrackerDB.loadTrackerItem();
        } else {
            trackerItems = new ArrayList<TrackerItem>();
            Log.d(TAG, "refreshing db");
        }
    }

    public static TrackerItemLab getsTrackerItemLab(Context context) {
        if (sTrackerItemLab == null) {
            sTrackerItemLab = new TrackerItemLab(context.getApplicationContext());
            return sTrackerItemLab;
        }
        return sTrackerItemLab;
    }

    public TrackerItem getTrackerItem(UUID uuid) {
        for (TrackerItem trackerItem : trackerItems) {
            if (trackerItem.getmId().equals(uuid)) {
                return trackerItem;
            }
        }
        return null;
    }

    public void addTrackItem(TrackerItem trackerItem) {
        trackerItems.add(trackerItem);
    }

    public void deleteTrackerItem(TrackerItem trackerItem) {
        trackerItems.remove(trackerItem);
    }

    public List<TrackerItem> getTrackerItems() {
        return trackerItems;
    }

    public boolean saveTrackerItems(TrackerItem ti) {
        TimeTrackerDB db = TimeTrackerDB.getInstance(mAppContext);
        try {
            db.saveTrackerItem(ti);
            Log.d(TAG, "save a TrackerItem");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
