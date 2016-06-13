package com.android.frankthirteen.timetracker.fragment;


import android.os.Bundle;
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
public class ReporterModifyFragment extends Fragment {

    private Tracker mTracker;
    private TextView tvTitle,tvContent,tvDuration,tvDate;

    public static ReporterModifyFragment newInstance(UUID uuid) {
        
        Bundle args = new Bundle();
        args.putSerializable(Tracker.EXTRA_ID, uuid);
        ReporterModifyFragment fragment = new ReporterModifyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ReporterModifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UUID mId = (UUID)getArguments().getSerializable(Tracker.EXTRA_ID);

        mTracker = TrackerLab.getTrackerLab(getActivity()).getTracker(mId);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reporter_modify, container, false);

        tvTitle = (TextView) rootView.findViewById(R.id.tracker_title);
        tvTitle.setText(mTracker.getTitle());


        return rootView;
    }

}
