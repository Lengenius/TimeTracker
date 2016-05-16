package com.android.frankthirteen.timetracker.model;

import android.content.Context;
import android.util.Log;

import com.android.frankthirteen.timetracker.db.TimeTrackerDB;
import com.android.frankthirteen.timetracker.utils.LogUtils;

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
    private TimeTrackerDB timeTrackerDB;
    private Context mAppContext;

    private List<TrackerItem> trackerItems;

    private TrackerItemLab(Context appContext) {
        mAppContext = appContext;
        timeTrackerDB = TimeTrackerDB.getInstance(mAppContext);
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
        LogUtils.d(TAG, "add item to lab");
        trackerItems.add(trackerItem);
        saveTrackerItems(trackerItem);
    }

    public void deleteTrackerItem(TrackerItem trackerItem) {
        trackerItems.remove(trackerItem);
        LogUtils.d(TAG, "there are items: " + trackerItems.size());
        timeTrackerDB.deleteTrackerItem(trackerItem);
    }

    public List<TrackerItem> getTrackingItems() {
        List<TrackerItem> trackingItems = new ArrayList<TrackerItem>();
        for (TrackerItem ti :
                trackerItems) {
            if (ti.getTrackingState()) {
                trackingItems.add(ti);
            }
        }
        return trackingItems;
    }

    public List<TrackerItem> getTrackedItems() {
        List<TrackerItem> trackedItems = new ArrayList<TrackerItem>();
        for (TrackerItem ti :
                trackerItems) {
            if (!ti.getTrackingState()) {
                trackedItems.add(ti);
            }
        }
        return trackedItems;
    }


    public boolean saveTrackerItems(TrackerItem ti) {
        timeTrackerDB.saveTrackerItem(ti);
        return true;
//        try {
//            timeTrackerDB.saveTrackerItem(ti);
//            Log.d(TAG, "save a TrackerItem" + ti.getmTitle());
//            return true;
//        } catch (Exception e) {
//            Log.d(TAG, "error saving trackerItem" + ti.getmTitle());
//            return false;
//        }
    }

    public void saveTrackerItemToDB() {
        for (TrackerItem ti : trackerItems) {
            saveTrackerItems(ti);
        }
    }
}
