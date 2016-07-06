package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.utils.FormatUtils;

import java.util.List;


/**
 * Created by Frank on 7/6/16.
 */
public class DurationItemAdapter extends RecyclerView.Adapter<DurationItemAdapter.ViewHolder> {

    private List<DurationItem> durationItemList;
    private Context mContext;

    public DurationItemAdapter(Context context, List<DurationItem> data) {
        durationItemList = data;
        mContext = context;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cell_duration_item,parent,false);

        ViewHolder vh = new ViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DurationItem di = durationItemList.get(position);
        holder.trackerTitle.setText(di.getTracker().getTitle());
        holder.tag.setText(di.getTag());

        holder.startTime.setText(FormatUtils.formatDateTime(di.getStartDate()));


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trackerTitle,tag,startTime,duration;

        public ViewHolder(View itemView) {
            super(itemView);

            trackerTitle = ((TextView) itemView.findViewById(R.id.di_cell_tracker_title));
            tag = ((TextView) itemView.findViewById(R.id.di_cell_tag));
            startTime = ((TextView) itemView.findViewById(R.id.di_cell_start_time));
            duration = ((TextView) itemView.findViewById(R.id.di_cell_duration));

        }
    }
}
