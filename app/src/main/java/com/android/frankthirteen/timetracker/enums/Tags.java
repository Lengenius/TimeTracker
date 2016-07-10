package com.android.frankthirteen.timetracker.enums;

import com.android.frankthirteen.timetracker.R;

/**
 * Created by Frank on 7/9/16.
 */
public enum Tags {
    SPORT(R.mipmap.ic_sport, "Sport"),
    ENTERTAINMENT(R.mipmap.ic_entertainment, "Entertainment"),
    WORK(R.mipmap.ic_work, "Work"),
    TRAFFIC(R.mipmap.ic_traffic, "Traffic"),
    STUDY(R.mipmap.ic_study, "Study"),
    HOBBY(R.mipmap.ic_hobby, "Hobby");
    private int resId;
    private String name;

    Tags(int id, String name) {
        this.resId = id;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

}
