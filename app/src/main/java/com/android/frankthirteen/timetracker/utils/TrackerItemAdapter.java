package com.android.frankthirteen.timetracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;

import java.util.ArrayList;

/**
 * Created by Frank on 4/12/16.
 */
public class TrackerItemAdapter extends ArrayAdapter<TrackerItem> {
    private int resourceId;

    public TrackerItemAdapter(Context context, int layoutResourceId, ArrayList<TrackerItem> trackerItems){
        super(context, layoutResourceId, trackerItems);
        resourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackerItem trackerItem = getItem(position);

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
            ViewHolder viewHolder = new ViewHolder(convertView);
        }

        return convertView;
    }




    public static class ViewHolder {
        public ImageView mImageImageView;
        public TextView mTitleTextView,mContentTextView,mDurationTextView;
        public ImageButton btnStart,btnPause,btnStop;

        public ViewHolder(View view){
            mImageImageView = (ImageView) view.findViewById(R.id.listitem_tracker_img);
            mTitleTextView = (TextView) view.findViewById(R.id.listitem_tracker_title);
            mContentTextView = (TextView) view.findViewById(R.id.listitem_tracker_content);
            mDurationTextView = (TextView) view.findViewById(R.id.listitem_tracker_duration);
            btnStart = (ImageButton) view.findViewById(R.id.listitem_tracker_btnStart);
            btnPause = (ImageButton) view.findViewById(R.id.listitem_tracker_btnPause);
            btnStop = (ImageButton) view.findViewById(R.id.listitem_tracker_btnStop);
        }
    }

}
