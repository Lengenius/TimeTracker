package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.List;


/**
 * Created by Frank on 7/6/16.
 */
public class DurationItemAdapter extends RecyclerView.Adapter<DurationItemAdapter.ViewHolder> {

    private static final String TAG = "Duration adapter";
    private List<DurationItem> durationItemList;
    private Context mContext;

    public interface OnItemClickListener {
        void onClick(View v, int position);

        void onLongClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;

    public DurationItemAdapter(Context context, List<DurationItem> data) {
        durationItemList = data;
        mContext = context;

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cell_duration_item, parent, false);

        ViewHolder vh = new ViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        DurationItem di = durationItemList.get(position);

        if (di.getTracker() != null) {
            holder.trackerTitle.setText(di.getTracker().getTitle());
        }
        if (di.getComment() != null) {
            holder.comment.setText(di.getComment());
        } else {
            holder.comment.setText(di.getTagValue());
        }
        holder.diTag.setImageResource(getTagPic(di));

        holder.startTime.setText(FormatUtils.formatDateTime(di.getStartDate()));
        holder.duration.setText(FormatUtils.formatTime(di.getDuration()));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();

                    onItemClickListener.onClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();

                    onItemClickListener.onLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return durationItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trackerTitle, comment, startTime, duration;
        public ImageView diTag;

        public ViewHolder(View itemView) {
            super(itemView);

            trackerTitle = ((TextView) itemView.findViewById(R.id.di_cell_tracker_title));
            comment = ((TextView) itemView.findViewById(R.id.di_cell_comment));
            startTime = ((TextView) itemView.findViewById(R.id.di_cell_start_time));
            duration = ((TextView) itemView.findViewById(R.id.di_cell_duration));
            diTag = (ImageView) itemView.findViewById(R.id.di_cell_tag_img);

        }
    }

    public void addItem(int pos, DurationItem di) {
        durationItemList.add(pos, di);
        notifyItemInserted(pos);
    }

    public void removeItem(int pos) {
        TrackerDB.getTrackerDB(mContext).removeDurationItem(durationItemList.get(pos));
        durationItemList.remove(pos);
        notifyItemRemoved(pos);

    }

    public void updateData(List<DurationItem> data) {
        LogUtils.d(TAG, data.size() + "items loaded.");
        durationItemList.clear();
        durationItemList.addAll(data);
        notifyDataSetChanged();
    }

    private int getTagPic(DurationItem di) {
        if (di.getTag() < 0x00ff) {

            switch (di.getTag()) {
                case 0:
                    return R.mipmap.ic_sport;
                case 1:
                    return R.mipmap.ic_entertainment;
                case 2:
                    return R.mipmap.ic_work;
                case 3:
                    return R.mipmap.ic_traffic;
                case 4:
                    return R.mipmap.ic_study;
                case 5:
                    return R.mipmap.ic_hobby;
                case 6:
                    return R.mipmap.ic_rest;
                default:
                    return 0;
            }
        } else {
            return 0;
        }
    }

}
