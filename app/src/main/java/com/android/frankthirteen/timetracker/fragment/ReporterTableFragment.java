package com.android.frankthirteen.timetracker.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporterTableFragment extends Fragment {

    private static final String TAG = "REPORTER_TABLE";
    private Tracker mTracker;
    private TextView tvTitle, tvContent, tvComment, tvTimePayed,tvDayPast;
    private ProgressBar prDayPast, prTimePayed;
    private ImageView trPhoto;
    private CheckBox checkBox;
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

        checkBox = (CheckBox) rootView.findViewById(R.id.checkbox_tracking_state);

        prDayPast = (ProgressBar) rootView.findViewById(R.id.reporter_progress_day_past);
        prTimePayed = (ProgressBar) rootView.findViewById(R.id.reporter_progress_time_payed);

        trPhoto = (ImageView) rootView.findViewById(R.id.reporter_tracker_photo);
        if (mTracker.getPhotoPath()!=null){
            Bitmap bitmap = PictureUtils.getThumbnail(trPhoto,mTracker.getPhotoPath());
            trPhoto.setImageBitmap(bitmap);
        }


        tvContent.setText(mTracker.getContent());
        tvComment.setText(mTracker.getComment());
//        LogUtils.d(TAG,mTracker.getComment());
        //
        int dayPast = getDayPast();

        String passedDay =dayPast + getResources().getString(R.string.reporter_table_day_passed);
        tvDayPast.setText(passedDay);
        String passedMinutes = mTracker.getTotalDurations()/60 + getResources().getString(R.string.minutes);
        tvTimePayed.setText(passedMinutes);

        if (mTracker.getPlannedTimeInMinutes()!=0) {
            float passed = mTracker.getTotalDurations()/60;
            float planned = mTracker.getPlannedTimeInMinutes();
            int progress = Math.round(100*(passed/planned));
            LogUtils.d(TAG,"setting progress bar." + progress);
            prTimePayed.setProgress(progress);
        }

        if (getDayPast()!=0){
            Date today = new Date();
            float passed = (mTracker.getStartDate().getTime() - today.getTime())/1000/60/60/24;
            float planned = (mTracker.getEndDate().getTime()-mTracker.getStartDate().getTime())
                    /1000/60/60/24;

            int progress = Math.round((passed/planned)*100);

            prDayPast.setProgress(progress);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mTracker.setTracking(false);
                    TrackerLab.getTrackerLab(getActivity()).updateTracker(mTracker);
                }
            }
        });
    }

    private int getDayPast() {
        Date today = new Date();
        long msPast = today.getTime() - mTracker.getStartDate().getTime();
        return Math.round(msPast/1000/60/60/24);
    }


}
