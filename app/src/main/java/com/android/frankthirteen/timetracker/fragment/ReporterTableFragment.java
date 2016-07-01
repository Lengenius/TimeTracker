package com.android.frankthirteen.timetracker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporterTableFragment extends Fragment {

    private static final String TAG = "REPORTER_TABLE";
    private Tracker mTracker;
    private TextView tvTitle, tvContent, tvComment, tvTimePayed,tvDayPast;
    private ProgressBar prDayPast, prTimePayed;
    private UUID mId;

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
        TrackerLab trackerLab = TrackerLab.getTrackerLab(getActivity());
        if (trackerLab.getTracker(mId) == null) {
            trackerLab.addTracker(new Tracker(mId));
        }
        mTracker = TrackerLab.getTrackerLab(getActivity()).getTracker(mId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reporter_table, container, false);

        initialView(rootView);



        return rootView;
    }

    private void initialView(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.reporter_tracker_title);
        tvTitle.setText(mTracker.getTitle());

        tvContent = (TextView) rootView.findViewById(R.id.reporter_tracker_content);
        tvComment = (TextView) rootView.findViewById(R.id.reporter_tracker_comment);
        tvDayPast = (TextView) rootView.findViewById(R.id.reporter_tv_day_past);
        tvTimePayed = (TextView) rootView.findViewById(R.id.reporter_tv_time_payed);

        prDayPast = (ProgressBar) rootView.findViewById(R.id.reporter_progress_day_past);
        prTimePayed = (ProgressBar) rootView.findViewById(R.id.reporter_progress_time_payed);

        tvContent.setText(mTracker.getContent());
        tvComment.setText(mTracker.getComment());
        LogUtils.d(TAG,mTracker.getComment());
        //
        tvDayPast.setText("Today - start Date");
        tvTimePayed.setText(mTracker.getTotalDurations() + "mins");
    }


}
