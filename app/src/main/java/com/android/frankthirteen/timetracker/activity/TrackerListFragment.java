package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    private static final String TAGS = "Started";
    private static final int REQUEST_DETAIL = 1;

    private List<TrackerItem> trackerItems;
    private TrackerItemAdapter trackerItemAdapter;
    private final Handler mHandler = new Handler();
    private Boolean anyStarted;

    public class TimerRunnable implements Runnable {
        private TrackerItem i;

        TimerRunnable(TrackerItem item) {
            i = item;
        }

        @Override
        public void run() {
            if (i.isStarted()) {
                i.increase();
                trackerItemAdapter.notifyDataSetChanged();
                mHandler.postDelayed(this, 1000);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerItems = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackerItems();
        if (trackerItems.size() == 0) {
            initialData();
            Log.d(TAG, "onCreate initialData");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (TrackerItem ti :trackerItems
             ) {

        TrackerItemLab.getsTrackerItemLab(getContext()).saveTrackerItems(ti);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trackerlist_menu, menu);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tracker_list_container, container, false);
        ListView trackerListView = (ListView) rootView.findViewById(R.id.tracker_listview);
        trackerItemAdapter = new TrackerItemAdapter(getActivity(), R.layout.listitem_tracker, trackerItems);
        trackerListView.setAdapter(trackerItemAdapter);
//        trackerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TrackerItem trackerItem = trackerItemAdapter.getItem(position);
//                Intent i = new Intent(getActivity(), TrackerDetailActivity.class);
//                i.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, trackerItem.getmId());
//            }
//        });
        Button btnAdd = (Button) rootView.findViewById(R.id.add_item);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackerItem item = new TrackerItem();
                item.setmTitle("new");
                trackerItems.add(item);
            }
        });
        return rootView;
    }

    public void initialData() {
        TrackerItem t1, t2;
        t1 = new TrackerItem(getActivity());
        t1.setmTitle("ta ");
//        t2 = new TrackerItem(getActivity());
//        t2.setmTitle("tb ");

        //They references to the same object.
        TrackerItemLab.getsTrackerItemLab(getContext()).addTrackItem(t1);
//        trackerItems.add(t2);
    }

    @Override
    public void onResume() {
        super.onResume();
        trackerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO bug when device is rotated.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO Bug fix picture didn't show when back.
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "detail result OK");
            trackerItemAdapter.notifyDataSetChanged();
            switch (requestCode) {
                case REQUEST_DETAIL:

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

            int hours, minutes, seconds, totalDuration;
            totalDuration = trackerItem.getmDuration();
            hours = totalDuration / 60 / 60;
            minutes = totalDuration / 60 % 60;
            seconds = totalDuration % 60;

            viewHolder.mDurationTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            viewHolder.mTitleTextView.setText(trackerItem.getmTitle());
            viewHolder.mContentTextView.setText(trackerItem.getmContent());
            viewHolder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startTimer(trackerItem);
                    viewHolder.btnStart.setVisibility(View.GONE);
                    viewHolder.btnPause.setVisibility(View.VISIBLE);
//                        startTimer(timerTask, trackerItem);
                }
            });

            viewHolder.btnPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseTimer(trackerItem);
                    viewHolder.btnPause.setVisibility(View.GONE);
                    viewHolder.btnStart.setVisibility(View.VISIBLE);
                }
            });

            viewHolder.btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTimer(trackerItem);
                    viewHolder.btnPause.setVisibility(View.GONE);
//                    Dialog dialog = new AlertDialog.Builder(getActivity())
//                            .setTitle("Is this activity end?")
//                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .show();
                }
            });

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

        private void stopTimer(TrackerItem trackerItem) {
            trackerItem.setmIsStarted(false);
            trackerItem.saveDuration();
            checkAnyStarted();
        }

        private void pauseTimer(TrackerItem trackerItem) {
            trackerItem.setmIsStarted(false);
            trackerItem.saveDuration();
            checkAnyStarted();
        }


        private void startTimer(TrackerItem item) {
            item.setmIsStarted(true);
            checkAnyStarted();
            mHandler.post(new TimerRunnable(item));
            Log.d(TAG, item + "start timer");
        }

        private void checkAnyStarted() {
            anyStarted = false;//refresh the anyStarted Variable to make sure it could be stopped.
            for (TrackerItem i :
                    trackerItems) {
                Log.d(TAGS, i + i.isStarted().toString());
                anyStarted = anyStarted || i.isStarted();
//                Log.d(TAGS, "2" + anyStarted.toString());
            }
        }


        public class ViewHolder {
            public View viewGroup;
            public ImageView mImageImageView;
            public TextView mTitleTextView, mContentTextView, mDurationTextView;
            public ImageButton btnStart, btnPause, btnStop;

            public ViewHolder(View view) {
                viewGroup = view;
                mImageImageView = (ImageView) view.findViewById(R.id.listitem_tracker_img);
                mTitleTextView = (TextView) view.findViewById(R.id.listitem_tracker_title);
                mContentTextView = (TextView) view.findViewById(R.id.listitem_tracker_content);
                mDurationTextView = (TextView) view.findViewById(R.id.listitem_tracker_duration);
                btnStart = (ImageButton) view.findViewById(R.id.listitem_tracker_btnStart);
                btnPause = (ImageButton) view.findViewById(R.id.listitem_tracker_btnPause);
                btnStop = (ImageButton) view.findViewById(R.id.listitem_tracker_btnStop);
            }
        }
    }
}
