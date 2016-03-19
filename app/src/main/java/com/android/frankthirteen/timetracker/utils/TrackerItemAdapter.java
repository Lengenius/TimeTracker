package com.android.frankthirteen.timetracker.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItemAdapter extends ArrayAdapter<TrackerItem> {


    public TrackerItemAdapter(Context context, int trackerItemViewId, List<TrackerItem> objects) {
        super(context, trackerItemViewId, objects);
        resourceId = trackerItemViewId;
    }

    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrackerItem trackerItem = getItem(position);
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();

            viewHolder.trackerImageView = (ImageView) view.findViewById(R.id.tracker_img);
            viewHolder.trackerTitleView = (TextView) view.findViewById(R.id.tracker_item_title);
            viewHolder.trackerContentView = (TextView) view.findViewById(R.id.tracker_item_content);
            viewHolder.trackerTimerView = (TextView) view.findViewById(R.id.tracker_item_timer);

            viewHolder.trackerBtnStart = (Button) view.findViewById(R.id.trackerBtnStart);
            viewHolder.trackerBtnStop = (Button) view.findViewById(R.id.trackerBtnStop);
            viewHolder.trackerBtnPause = (Button) view.findViewById(R.id.trackerBtnPause);
            viewHolder.trackerBtnPause.setVisibility(View.GONE);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.trackerImageView.setImageResource(trackerItem.getItemImage());
        viewHolder.trackerTitleView.setText(trackerItem.getTitle());
        viewHolder.trackerContentView.setText(trackerItem.getContent());
        viewHolder.trackerBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.trackerTimerView.setText("changed");
//                startTimer();
            }
        });

        viewHolder.trackerBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;

    }

    private int tmp = 0;
    private static final int MSG_SHOW_TIME = 1;
    private static final int MSG_STOP_TIME = 2;
    private int resourceId;


    private void startTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                tmp = tmp + 1;
                handler.post(updateTimer);
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private Handler handler = new Handler();
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            viewHolder.showTimer(tmp);
        }
    };

/*
    private class ViewHolder {
        ImageView trackerImageView;
        TextView trackerTitleView;
        TextView trackerContentView;
        TextView trackerTimerView;

        Button trackerBtnStart;
        Button trackerBtnStop;
        Button trackerBtnPause;

        public void showTimer(int tmp){
            trackerTimerView.setText(tmp);
        }
    }*/
}
