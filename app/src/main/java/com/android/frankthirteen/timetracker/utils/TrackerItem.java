package com.android.frankthirteen.timetracker.utils;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItem {
    private String title;
    private String content;
    private int itemImage;

    private int timerSeconds;

    public TrackerItem (String title, String content, int itemImage, int timerSeconds) {
        this.title = title;
        this.content = content;
        this.itemImage = itemImage;
        this.timerSeconds = timerSeconds;
    }

    public String getTitle(){
        return title;
    }

    public  String getContent(){
        return content;
    }

    public int getItemImage(){
        return itemImage;
    }

    public int getTimerSeconds(){
        return timerSeconds;
    }

}
