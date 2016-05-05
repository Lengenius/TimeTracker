package com.android.frankthirteen.timetracker.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/5/16.
 */
public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity a : activities) {
            if (!a.isFinishing()) {
                a.finish();
            }
        }
    }

}
