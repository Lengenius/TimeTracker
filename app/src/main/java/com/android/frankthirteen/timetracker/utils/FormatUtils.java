package com.android.frankthirteen.timetracker.utils;

/**
 * Created by Frank on 5/25/16.
 */
public class FormatUtils {

    public static String timerFormat(int duration){
        int hours,minutes,seconds;
        hours = duration/60/60;
        minutes = duration/60%60;
        seconds = duration%60;

        return String.format("%02d:%02d:%02d",hours,minutes,seconds);


    }








}
