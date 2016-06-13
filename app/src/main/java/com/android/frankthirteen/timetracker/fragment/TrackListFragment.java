package com.android.frankthirteen.timetracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.activity.ReporterActivity;
import com.android.frankthirteen.timetracker.adapter.DividerItemDecoration;
import com.android.frankthirteen.timetracker.adapter.RecyclerViewAdapter;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;

import java.util.ArrayList;

/**
 * Created by Frank on 5/26/16.
 */
public class TrackListFragment extends Fragment {
    public static final int TRACKING = 0;
    public static final int FINISHED = 1;


    private TrackerLab trackerLab;
    private ArrayList<Tracker> trackers;

    public static TrackListFragment newInstance() {

        Bundle args = new Bundle();

        TrackListFragment fragment = new TrackListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerLab = TrackerLab.getTrackerLab(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //should use different sign to load different trackers.
        View rootView = inflater.inflate(R.layout.fragment_list_tracker, null);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.tracker_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        trackers = getTrackers(TRACKING);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), trackers);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ReporterActivity.class);
                intent.putExtra(Tracker.EXTRA_ID,trackers.get(position).getId());

                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(getActivity(), "item long clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;

    }

    private ArrayList<Tracker> getTrackers(int state) {
        ArrayList<Tracker> trackers;

        if (state != TRACKING && state != FINISHED) {
            throw new IllegalArgumentException("Invalid argument");
        }
        if (state == TRACKING){
            trackers = trackerLab.getTrackingTrackers();
        }else {
            trackers = trackerLab.getTrackedTrackers();
        }

        return trackers;
    }
}
