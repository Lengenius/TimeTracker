package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 5/25/16.
 */
public class TrackerLab {
    private static final String TAG = "LAB";
    private static TrackerLab sTrackerLab;
    private Context mContext;
    private List<Tracker> trackers;
    private TrackerDB trackerDB;


    private TrackerLab(Context context) {
        mContext = context;
        trackers = new ArrayList<Tracker>();
        //Load DB if possible; initial the data.

        trackerDB = TrackerDB.getTrackerDB(mContext);

        if (trackerDB != null) {
            initialTrackers();
        }
    }


    public static TrackerLab getTrackerLab(Context context) {
        if (sTrackerLab == null) {
            sTrackerLab = new TrackerLab(context.getApplicationContext());

        }
        return sTrackerLab;
    }

    public ArrayList<Tracker> getTrackedTrackers() {
        ArrayList<Tracker> tracked = new ArrayList<Tracker>();
        for (Tracker ti : trackers) {
            if (!ti.isTracking()) {
                tracked.add(ti);
            }
        }
        return tracked;
    }

    public ArrayList<Tracker> getTrackingTrackers() {
        ArrayList<Tracker> tracking = new ArrayList<Tracker>();
        for (Tracker ti :
                trackers) {
            if (ti.isTracking()) {
                tracking.add(ti);
            }
        }
        return tracking;
    }

    public void addTracker(Tracker tracker) {
        trackers.add(tracker);
        trackerDB.insertTracker(tracker);
        //add to DB to.
    }

    public void removeTracker(Tracker tracker) {
        trackers.remove(tracker);
        trackerDB.removeTracker(tracker);
        trackerDB.removeDurationItemsByTracker(tracker);
        //also need to remove it from DB.
    }

    public void removeTracker(UUID trackerId) {
        for (Tracker tr :
                trackers) {
            if (tr.getId().equals(trackerId)) {

                trackers.remove(tr);
                trackerDB.removeTracker(tr);
                trackerDB.removeDurationItemsByTracker(tr);
            }
        }
        //also need to remove it from DB.
    }

    public Tracker getTracker(UUID uuid) {
        for (Tracker ti :
                trackers) {
            LogUtils.d(TAG, "uuid is " + ti.getId().toString());
            //UUID should use equals
            if (uuid.equals(ti.getId())) {
                LogUtils.d(TAG, "Found uuid is i:" + ti.getId().toString());
                return ti;
            }
        }
        LogUtils.d(TAG, "unfortunately ");
        return null;
    }

    public Tracker getLastTracker() {

        return trackers.get(trackers.size() - 1);
    }

    private void initialTrackers() {

        trackers = trackerDB.getTrackers();
        for (Tracker tr :
                trackers) {
            LogUtils.d(TAG, "getting tracker\'s durations.");
            //it may cause performance problem. it
            tr.setDurationItems(trackerDB.getDurationItemsByTracker(tr));
        }

    }


    private void fakeData() {
        Tracker trackerMeaning = new Tracker();
        trackerMeaning.setTitle(mContext.getString(R.string.tracker_meaning_title));
        trackerMeaning.setContent(mContext.getString(R.string.tracker_meaning_content));
        trackers.add(trackerMeaning);

        Tracker trackerOperation = new Tracker();
        trackerOperation.setTitle(mContext.getString(R.string.tracker_operation_title));
        trackerOperation.setContent(mContext.getString(R.string.tracker_operation_content));
        trackers.add(trackerOperation);

        Tracker createTracker = new Tracker();
        createTracker.setTitle(mContext.getString(R.string.tracker_create_title));
        createTracker.setContent(mContext.getString(R.string.tracker_create_content));
        trackers.add(createTracker);

    }

    public void updateTracker(Tracker mTracker) {
        trackerDB.updateTracker(mTracker);
    }
}
