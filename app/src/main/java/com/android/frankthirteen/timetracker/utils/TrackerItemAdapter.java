package com.android.frankthirteen.timetracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;

import java.util.List;

/**
 * Created by Frank on 3/9/16.
 */
public class TrackerItemAdapter extends ArrayAdapter<TrackerItem> {

    private int resourceId;

    public TrackerItemAdapter (Context context, int trackerItemViewId, List<TrackerItem> objects){

        super(context, trackerItemViewId, objects);
        resourceId = trackerItemViewId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackerItem trackerItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ImageView trackerImageView = (ImageView) view.findViewById(R.id.tracker_img);
        TextView trackerTitleView = (TextView) view.findViewById(R.id.tracker_item_title);
        TextView trackerContentView = (TextView) view.findViewById(R.id.tracker_item_content);
        TextView trackerTimerView = (TextView) view.findViewById(R.id.tracker_item_timer);

        Button btnStart = (Button) view.findViewById(R.id.btnStart);
        Button btnStop = (Button) view.findViewById(R.id.btnStop);

        trackerImageView.setImageResource(trackerItem.getItemImage());
        trackerTitleView.setText(trackerItem.getTitle());
        trackerContentView.setText(trackerItem.getContent());
        return view;
    }
}
