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
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private Context mContext;
    private List<String> tags;
    private LayoutInflater layoutInflater;
    private OnTagItemClickListener mOnTagItemClickListener;

    public interface OnTagItemClickListener{
        void onTagItemClick(View v,int position);
    }

    public TagAdapter(Context context, List<String> tags){
        mContext = context;
        this.tags = tags;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnTagItemClickListener onTagItemClickListener){

        this.mOnTagItemClickListener = onTagItemClickListener;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        TagViewHolder tagViewHolder = new TagViewHolder(view);
        return tagViewHolder;
    }

    @Override
    public void onBindViewHolder(final TagViewHolder holder, int position) {

        holder.tag.setText(tags.get(position));
        if(mOnTagItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();

                    mOnTagItemClickListener.onTagItemClick(holder.itemView,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        public TagViewHolder(View itemView) {
            super(itemView);

            tag = ((TextView) itemView.findViewById(R.id.simple_tag_tv));
        }
    }

    public String getItem(int position){
        return tags.get(position);
    }
}
