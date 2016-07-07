package com.android.frankthirteen.timetracker.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

        if (trackerDB!=null){
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
        if (trackers.size()==0){
            fakeData();

        }
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
        //add to DB to.
    }

    public void removeTracker(Tracker tracker) {
        trackers.remove(tracker);
        trackerDB.removeTracker(tracker);
        //also need to remove it from DB.
    }

    public void removeTracker(UUID trackerId) {
        for (Tracker tr :
                trackers) {
            if (tr.getId().equals(trackerId)){

                trackers.remove(tr);
                trackerDB.removeTracker(tr);
            }
        }
        //also need to remove it from DB.
    }

    public Tracker getTracker(UUID uuid){
        for (Tracker ti :
                trackers) {
            LogUtils.d(TAG, "uuid is "+ti.getId().toString());
            //UUID should use equals
            if (uuid.equals(ti.getId())) {
                LogUtils.d(TAG,"Found uuid is i:" + ti.getId().toString());
                return ti;
            }
        }
        LogUtils.d(TAG,"unfortunately ");
        return null;
    }

    public Tracker getLastTracker(){

        return trackers.get(trackers.size()-1);
    }

    private void initialTrackers() {

        trackers = trackerDB.getTrackers();
        for (Tracker tr :
                trackers) {
            LogUtils.d(TAG,"getting tracker\'s durations.");
            tr.setDurationItems(trackerDB.getDurationItemsByTracker(tr));
        }

    }



    private void fakeData(){
        for(int i=0;i<3;i++){
            Tracker fakeTracker = new Tracker();
            fakeTracker.setTitle("This is "+i);
            fakeTracker.setContent("there are "+i+"items.");
            trackers.add(fakeTracker);
        }
    }

}
