package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;

import java.util.List;

/**
 * Created by Frank on 6/19/16.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private Context mContext;
    private List<String> tags;
    private LayoutInflater layoutInflater;

    public TagAdapter(Context context, List<String> tags){
        mContext = context;
        this.tags = tags;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tag.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        public ViewHolder(View itemView) {
            super(itemView);

            tag = ((TextView) itemView.findViewById(R.id.simple_tag_tv));
        }
    }
}
