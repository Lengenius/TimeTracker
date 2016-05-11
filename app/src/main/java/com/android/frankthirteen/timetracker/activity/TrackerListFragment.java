package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.util.List;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    private static final int REQUEST_DETAIL = 1;
    private static final int REQUEST_DURATION = 2;

    private List<TrackerItem> trackerItems;
    private TrackerItemAdapter trackerItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerItems = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackingItems();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trackerlist_menu, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tracker_list_container, container, false);
        ListView trackerListView = (ListView) rootView.findViewById(R.id.tracker_listView);

        trackerItemAdapter = new TrackerItemAdapter(getActivity(), R.layout.listitem_tracker, trackerItems);
        trackerListView.setAdapter(trackerItemAdapter);

        Button btnAdd = (Button) rootView.findViewById(R.id.add_item);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTracker();
            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
//        trackerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO bug when device is rotated.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DETAIL:
                    Log.d(TAG,"add new item");
                    trackerItemAdapter.notifyDataSetChanged();
                    TrackerItemLab.getsTrackerItemLab(getActivity()).saveTrackerItemToDB();
                    break;
                case REQUEST_DURATION:
                    break;
                default:
                    break;
            }
        }
    }

    private class TrackerItemAdapter extends ArrayAdapter<TrackerItem> {
        private int resourceId;

        public TrackerItemAdapter(Context context, int layoutResourceId, List<TrackerItem> trackerItems) {
            super(context, layoutResourceId, trackerItems);
            resourceId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TrackerItem trackerItem = getItem(position);
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
                    Intent intent = new Intent(getActivity(), WorkFocusActivity.class);
                    intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, trackerItem.getmId());
                    startActivityForResult(intent, REQUEST_DURATION);
                }
            });
            viewHolder.mContentTextView.setText(trackerItem.getmContent());

            viewHolder.mImageImageView.setClickable(true);
            if (trackerItem.getmPhoto() != null) {
                BitmapDrawable bitmap = PictureUtils.getScaledPic(getActivity(), trackerItem.getmPhoto().getmPhotoPath());
                viewHolder.mImageImageView.setImageDrawable(bitmap);
            }
            viewHolder.mImageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TrackerDetailActivity.class);
                    intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, trackerItem.getmId());
                    startActivityForResult(intent, REQUEST_DETAIL);
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
                mImageImageView = (ImageView) view.findViewById(R.id.listitem_tracker_img);
                mTitleTextView = (TextView) view.findViewById(R.id.listitem_tracker_title);
                mContentTextView = (TextView) view.findViewById(R.id.listitem_tracker_content);
                mDurationTextView = (TextView) view.findViewById(R.id.listitem_tracker_duration);
            }
        }
    }



    private void createNewTracker(){
        TrackerItem item = new TrackerItem();
        TrackerItemLab.getsTrackerItemLab(getActivity()).addTrackItem(item);
        Intent intent = new Intent(getActivity(),TrackerDetailActivity.class);
        intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID,item.getmId());
        startActivityForResult(intent,REQUEST_DETAIL);
    }
}
