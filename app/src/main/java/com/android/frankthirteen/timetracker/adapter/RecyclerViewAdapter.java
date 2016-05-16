package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.util.ArrayList;

/**
 * Created by Frank on 5/16/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TrackerItem> trackerItems;

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
        trackerItems = TrackerItemLab.getsTrackerItemLab(mContext).getTrackedItems();
    }

    @Override
    public int getItemCount() {
        return trackerItems.size();
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
        TrackerItem item = trackerItems.get(position);

        holder.mTitle.setText(item.getmTitle());
        holder.mDuration.setText(FormatUtils.formatDuration(item.getmDuration()));
        holder.mContent.setText(item.getmContent());
//        if (item.getmPhoto()!=null) {
//            BitmapDrawable bitmapDrawable =
//            holder.mPhoto.setImageBitmap();
//        }
    }
}
