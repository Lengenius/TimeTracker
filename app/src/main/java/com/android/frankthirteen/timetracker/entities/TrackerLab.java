package com.android.frankthirteen.timetracker.entities;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Frank on 5/25/16.
 */
public class TrackerLab {
    private static TrackerLab sTrackerLab;
    private Context mContext;
    private ArrayList<Tracker> trackers;


    private TrackerLab(Context context) {
        mContext = context;
        trackers = new ArrayList<Tracker>();
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
    }

    public void removeTracker(Tracker tracker) {
        trackers.remove(tracker);
    }

    public Tracker getTracker(UUID uuid){
        for (Tracker ti :
                trackers) {
            if (ti.getId() == uuid) {
                return ti;
            }
        }
        return null;
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
