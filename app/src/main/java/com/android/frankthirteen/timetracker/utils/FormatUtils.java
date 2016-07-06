package com.android.frankthirteen.timetracker.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Frank on 5/25/16.
 */
public class FormatUtils {
    private static Calendar calendar;

    public static String timerFormat(int duration){
        int hours,minutes,seconds;
        hours = duration/60/60;
        minutes = duration/60%60;
        seconds = duration%60;

        return String.format("%02d:%02d:%02d",hours,minutes,seconds);


    }

    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd EEE", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd EEE \nHH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }



    public static int getDayOfWeek(Date date){
        calendar = Calendar.getInstance();

        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }








}
