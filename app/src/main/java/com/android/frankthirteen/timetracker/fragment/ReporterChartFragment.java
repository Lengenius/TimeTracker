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
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterChartFragment extends Fragment {

    private Tracker mTracker;

    public static ReporterChartFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();

        args.putSerializable(Tracker.EXTRA_ID,uuid);
        ReporterChartFragment fragment = new ReporterChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReporterChartFragment(){
        //Constructor

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UUID id = (UUID) getArguments().getSerializable(Tracker.EXTRA_ID);
        LogUtils.d("UUID","uuid is:" + id.toString());
        TrackerLab trackerLab = TrackerLab.getTrackerLab(getActivity());
        mTracker = trackerLab.getTracker(id);
        mTracker.getId();

        View rootView = inflater.inflate(R.layout.fragment_reporter_chart,null);
        TextView tv = (TextView) rootView.findViewById(R.id.reporter_chart_title);
        tv.setText(mTracker.getTitle());

        return rootView;
    }
}
