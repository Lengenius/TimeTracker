package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;

import java.util.List;

/**
 * Created by Frank on 7/12/16.
 */
public class TrackerTitleAdapter extends RecyclerView.Adapter<TrackerTitleAdapter.ViewHolder> {

    private Context mContext;
    private List<Tracker> trackers;


    public interface OnItemClickListener {
        void onClick(View v, int position);

        void onLongClick(View v, int position);

    }

    private OnItemClickListener mOnItemCLickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemCLickListener = onItemClickListener;
    }


    public TrackerTitleAdapter(Context context, List<Tracker> trackers) {
        mContext = context;
        this.trackers = trackers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cell_tracker_title, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Tracker tr = trackers.get(position);

        if (tr.getTitle() != null) {
            holder.trTitle.setText(trackers.get(position).getTitle());
        } else {
            String title = holder.getLayoutPosition() +
                    mContext.getString(R.string.tracker_title_sequence);
            holder.trTitle.setText(title);
        }
        if (mOnItemCLickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = holder.getLayoutPosition();
                    mOnItemCLickListener.onClick(holder.itemView, position);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemCLickListener.onLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return trackers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView trTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            trTitle = (TextView) itemView.findViewById(R.id.tr_title);
        }
    }
}
