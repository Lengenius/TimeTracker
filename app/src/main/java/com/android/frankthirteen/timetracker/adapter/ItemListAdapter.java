package com.android.frankthirteen.timetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.activity.TrackerDetailActivity;
import com.android.frankthirteen.timetracker.fragment.TrackerDetailFragment;
import com.android.frankthirteen.timetracker.fragment.TrackerListFragment;
import com.android.frankthirteen.timetracker.activity.WorkFocusActivity;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 5/13/16.
 * Adapter to hold tracking items.
 */
public class ItemListAdapter extends ArrayAdapter<TrackerItem> {
    private static final String TAG = "list Adapter:";
    private TrackerListFragment trackerListFragment;
    private int resourceId;

    public ItemListAdapter(TrackerListFragment trackerListFragment, Context context, int layoutResourceId, List<TrackerItem> trackerItems) {
        super(context, layoutResourceId, trackerItems);
        this.trackerListFragment = trackerListFragment;
        resourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackerItem trackerItem = getItem(position);
        final UUID id = trackerItem.getmId();
        View view;
        final ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mDurationTextView.setText(FormatUtils.formatDuration(trackerItem.getmDuration()));
        viewHolder.mTitleTextView.setText(trackerItem.getmTitle());
        viewHolder.mDurationTextView.setClickable(true);
        viewHolder.mDurationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trackerListFragment.getActivity(), WorkFocusActivity.class);
                intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, id);
                trackerListFragment.startActivityForResult(intent, TrackerListFragment.REQUEST_DURATION);
            }
        });
        viewHolder.mContentTextView.setText(trackerItem.getmContent());

        viewHolder.mImageImageView.setClickable(true);
        LogUtils.d(TAG,"tracker item"+trackerItem.getmId().toString());
        LogUtils.d(TAG,"has photo ?" + trackerItem.getmPhoto());
        if (trackerItem.getmPhoto() != null) {
//            BitmapDrawable bitmap = PictureUtils.getScaledPic(trackerListFragment.getActivity(), trackerItem.getmPhoto().getmPhotoPath());
//            viewHolder.mImageImageView.setImageDrawable(bitmap);
            Bitmap bitmap = PictureUtils.getThumbnail(viewHolder.mImageImageView,trackerItem.getmPhoto().getmPhotoPath());
            viewHolder.mImageImageView.setImageBitmap(bitmap);
        }else{
            //Block Image.
            viewHolder.mImageImageView.setImageResource(R.mipmap.ic_launcher);
        }
        viewHolder.mImageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trackerListFragment.getActivity(), TrackerDetailActivity.class);
                intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, id);
                trackerListFragment.startActivityForResult(intent, TrackerListFragment.REQUEST_DETAIL);
            }
        });


        return view;
    }

    public class ViewHolder {
        public View viewGroup;
        public ImageView mImageImageView;
        public TextView mTitleTextView, mContentTextView, mDurationTextView;

        public ViewHolder(View view) {
            viewGroup = view;
            mImageImageView = (ImageView) view.findViewById(R.id.cell_thumbnail);
            mTitleTextView = (TextView) view.findViewById(R.id.listitem_tracker_title);
            mContentTextView = (TextView) view.findViewById(R.id.listitem_tracker_content);
            mDurationTextView = (TextView) view.findViewById(R.id.listitem_tracker_duration);
        }
    }
}
