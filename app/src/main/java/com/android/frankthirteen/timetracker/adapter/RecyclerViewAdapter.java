package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;

import java.util.ArrayList;

/**
 * Created by Frank on 5/16/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Tracker> trackers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //TODO initial the viewHolder's object.
        public LinearLayout rootView;
        public TextView mTitle, mContent, mDuration;
        public ImageView mPhoto;

        public ViewHolder(View view) {
            super(view);
            rootView = (LinearLayout) view;
            mTitle = (TextView) rootView.findViewById(R.id.listitem_tracker_title);
            mContent = (TextView) rootView.findViewById(R.id.listitem_tracker_content);
            mDuration = (TextView) rootView.findViewById(R.id.listitem_tracker_duration);
            mPhoto = (ImageView) rootView.findViewById(R.id.cell_thumbnail);
        }
    }

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        trackers = TrackerLab.getTrackerLab(context).getTrackingTrackers();
    }

    @Override
    public int getItemCount() {
        return trackers.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_tracker, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO bind view and item's content.
        Tracker item = trackers.get(position);

        holder.mTitle.setText(item.getmTrackerTitle());
        holder.mContent.setText(item.getmTrackerContent());
//        if (item.getmPhoto()!=null) {
//            BitmapDrawable bitmapDrawable =
//            holder.mPhoto.setImageBitmap();
//        }
    }
}
