package com.android.frankthirteen.timetracker.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

    public TrackerItemAdapter(Context context, int trackerItemViewId, List<TrackerItem> objects) {

        super(context, trackerItemViewId, objects);
        resourceId = trackerItemViewId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TrackerItem trackerItem = getItem(position);
        final ViewHolder viewHolder;
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
            }
        });

      /*  Handler handler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case TrackerItem.MSG_SHOW_TIMER:
                        viewHolder.trackerTimerView.setText("changed");
                }
            }
        };*/

        return view;
    }


    class ViewHolder {
        ImageView trackerImageView;
        TextView trackerTitleView;
        TextView trackerContentView;
        TextView trackerTimerView;

        Button trackerBtnStart;
        Button trackerBtnStop;
    }
}
