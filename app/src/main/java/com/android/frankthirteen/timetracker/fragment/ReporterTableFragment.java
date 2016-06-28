package com.android.frankthirteen.timetracker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporterTableFragment extends Fragment {

    private Tracker mTracker;
    private TextView tvTitle, tvContent, tvDuration, tvDate;
    private UUID mId;
    private TrackerLab trackerLab;

    public ReporterTableFragment() {
        // Required empty public constructor
    }

    public static ReporterTableFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(Tracker.EXTRA_ID, uuid);
        ReporterTableFragment fragment = new ReporterTableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (UUID) getArguments().getSerializable(Tracker.EXTRA_ID);
        trackerLab = TrackerLab.getTrackerLab(getActivity());
        if (trackerLab.getTracker(mId) == null){
            trackerLab.addTracker(new Tracker(mId));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mTracker = TrackerLab.getTrackerLab(getActivity()).getTracker(mId);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reporter_table, container, false);

        tvTitle = (TextView) rootView.findViewById(R.id.tracker_title);
        tvTitle.setText(mTracker.getTitle());


        return rootView;
    }

}
