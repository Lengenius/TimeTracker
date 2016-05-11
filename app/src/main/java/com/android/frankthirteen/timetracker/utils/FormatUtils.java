package com.android.frankthirteen.timetracker.utils;

/**
 * Created by Frank on 5/5/16.
 */
public class FormatUtils {
    public static String formatDuration(int duration){
        int hours, mins, seconds;
        int totalDuration;
        totalDuration = duration;
        hours = totalDuration/60/60;
        mins = totalDuration/60%60;
        seconds = totalDuration%60;

        return String.format("%02d:%02d:%02d", hours, mins,seconds);
    }

    public static String formatDurationInHour(int duration){
        int hours, mins;
        int totalDuration;
        totalDuration = duration;
        hours = totalDuration/60/60;
        mins = totalDuration/60%60;

        return String.format("%02d:%02d", hours, mins);
    }
}
