package com.android.frankthirteen.timetracker.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.activity.ReporterActivity;
import com.android.frankthirteen.timetracker.adapter.DividerItemDecoration;
import com.android.frankthirteen.timetracker.adapter.TrackerListAdapter;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by Frank on 5/26/16.
 */
public class TrackListFragment extends Fragment {
    public static final int TRACKING = 0;
    public static final int FINISHED = 1;

    public static final String TRACKER_ADDED = "com.android.frankthirteen.timetracker.fragment." +
            "TRACKER_ADDED";
    private static final String TAG = "TRACKER_LIST";
    private static final String TRACKING_STATE = "com.android.frankthirteen.timetracker.fragment."
            + "ListState";


    private int flagState = 0;
    private TrackerLab trackerLab;
    private ArrayList<Tracker> trackers;
    private TrackerListAdapter adapter;

    private TrackerAddReceiver trackerAddReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private RecyclerView recyclerView;

    public static TrackListFragment newInstance(int state) {

        Bundle args = new Bundle();
        args.putInt(TRACKING_STATE, state);

        TrackListFragment fragment = new TrackListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flagState = getArguments().getInt(TRACKING_STATE);
        trackerLab = TrackerLab.getTrackerLab(getActivity());
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        trackerAddReceiver = new TrackerAddReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TRACKER_ADDED);
        localBroadcastManager.registerReceiver(trackerAddReceiver,
                intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //should use different sign to load different trackers.
        View rootView = inflater.inflate(R.layout.fragment_list_tracker, null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.tracker_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        trackers = getTrackers(flagState);

        adapter = new TrackerListAdapter(getActivity(), trackers);
        adapter.setOnItemClickListener(new TrackerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReporterActivity.class);
                intent.putExtra(Tracker.EXTRA_ID, trackers.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                final Tracker removedTracker = adapter.getItem(position);
                new AlertDialog.Builder(getActivity())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteItem(position);
                                trackerLab.removeTracker(removedTracker);
                                Toast.makeText(getActivity(),
                                        R.string.toast_tracker_deleted, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .setTitle(R.string.title_delete_tracker)
                        .setMessage(R.string.message_dialog_delete_tracker)
                        .show();


            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return rootView;

    }

    //Use broadcast receiver to notify item insert.

    private ArrayList<Tracker> getTrackers(int state) {
        ArrayList<Tracker> trackers;

        if (state != TRACKING && state != FINISHED) {
            throw new IllegalArgumentException("Invalid argument");
        }
        if (state == TRACKING) {
            trackers = trackerLab.getTrackingTrackers();
        } else {
            trackers = trackerLab.getTrackedTrackers();
        }

        return trackers;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(trackerAddReceiver);
    }

    class TrackerAddReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.addItem(trackers.size(), trackerLab.getLastTracker());
        }
    }
}
