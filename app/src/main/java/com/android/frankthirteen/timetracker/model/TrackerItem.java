package com.android.frankthirteen.timetracker.model;

import java.util.GregorianCalendar;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItem {

    public TrackerItem(String title, String content, int itemImage, int timerSeconds) {
        this.title = title;
        this.content = content;
        this.itemImage = itemImage;
        initial();
    }

    private void initial() {
        GregorianCalendar now = new GregorianCalendar();
        id = now.getTimeInMillis();

    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getItemImage() {
        return itemImage;
    }

    public long getId(){
        return id;
    }

    private String title;
    private String content;
    private int itemImage;
    private long id;
}
