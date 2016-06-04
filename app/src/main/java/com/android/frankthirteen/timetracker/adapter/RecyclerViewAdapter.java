package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        trackers = TrackerLab.getTrackerLab(mContext).getTrackingTrackers();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_list_tracker, parent, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        // set viewHolder size and so on.

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.cellImage.setImageDrawable(tracker.getPhoto());

        Tracker tracker = trackers.get(position);
        holder.title.setText(tracker.getTitle());
        holder.content.setText(tracker.getContent());
        holder.timer.setText(tracker.getTotalDurations());
        if (mOnItemClickListener!=null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();

                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();

                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return true;
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return trackers.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView timer;
        public ImageView cellImage;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cell_title);
            content = (TextView) itemView.findViewById(R.id.cell_content);
            timer = (TextView) itemView.findViewById(R.id.cell_timer);
            cellImage = (ImageView) itemView.findViewById(R.id.cell_image);
        }
    }

}
