package com.android.frankthirteen.timetracker.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItemLab {
    private static TrackerItemLab sTrackerItemLab;
    private Context mAppContex;

    private ArrayList<TrackerItem> trackerItems;

    private TrackerItemLab(Context appContext){
        //Singleton
        mAppContex = appContext;
    }

    public static TrackerItemLab getsTrackerItemLab(Context context){
        if (sTrackerItemLab == null){
            sTrackerItemLab = new TrackerItemLab(context.getApplicationContext());
            return sTrackerItemLab;
        }
        return sTrackerItemLab;
    }

    public TrackerItem getTrackerItem(UUID uuid){
        for (TrackerItem trackerItem:trackerItems) {
            if (trackerItem.getmId().equals(uuid)){
                return trackerItem;
            }
        }
        return null;
    }

    public void addTrackItem(TrackerItem trackerItem){
        trackerItems.add(trackerItem);
    }

    public void deleteTrackerItem(TrackerItem trackerItem){
        trackerItems.remove(trackerItem);
    }

    public ArrayList<TrackerItem> getTrackerItems(){
        return trackerItems;
    }
}
