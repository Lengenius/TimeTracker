package com.android.frankthirteen.timetracker.activity;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.utils.TrackerItem;
import com.android.frankthirteen.timetracker.utils.TrackerItemLab;

import java.util.ArrayList;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListFragment extends Fragment {
    private static final String TAG = "Fragment";
    private static final String TAGS = "Started";
    private ArrayList<TrackerItem> trackerItems;
    private TrackerItemAdapter trackerItemAdapter;
    private final Handler mHandler = new Handler();
    private Boolean anyStarted;
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case START_TRACKER:
//                    trackerItemAdapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

//    final private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if ((trackerItems != null) && anyStarted) {
//                for (TrackerItem i :
//                        trackerItems) {
//                    if (i.isStarted()) {
//                        i.increase();
//                        Log.d(TAG, i + String.format("%d",i.getmDuration()));
//                        trackerItemAdapter.notifyDataSetChanged();
//                    }
//                }
////                Log.d(TAG, "Adapter changed");
////                Log.d(TAGS,anyStarted.toString());
//                mHandler.postDelayed(this, 1000);
//            }
//        }
//    };

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
        initialData();
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
        Log.d(TAG, "create View");
        return rootView;
    }

    public void initialData() {
        TrackerItem t1, t2;
        t1 = new TrackerItem();
        t1.setmTitle("ta ");
        t2 = new TrackerItem();
        t2.setmTitle("tb ");

        trackerItems.add(t1);
        trackerItems.add(t2);
    }

    private class TrackerItemAdapter extends ArrayAdapter<TrackerItem> {
        private int resourceId;

        public TrackerItemAdapter(Context context, int layoutResourceId, ArrayList<TrackerItem> trackerItems) {
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
                    stopTimer(trackerItem);
                    viewHolder.btnPause.setVisibility(View.GONE);
                    viewHolder.btnStart.setVisibility(View.VISIBLE);
                }
            });

            return view;
        }

        private void stopTimer(TrackerItem trackerItem) {
            trackerItem.setmIsStarted(false);
            Log.d(TAG, trackerItem + trackerItem.isStarted().toString());
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
            public ImageView mImageImageView;
            public TextView mTitleTextView, mContentTextView, mDurationTextView;
            public ImageButton btnStart, btnPause, btnStop;

            public ViewHolder(View view) {
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
