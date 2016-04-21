package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;

import java.util.UUID;

/**
 * Created by Frank on 4/17/16.
 */
public class TrackerDetailFragment extends Fragment {
    public static String EXTRA_TRACKER_ID = "com.android.frankthirteen.timetracker.trackerItemId";

    private TrackerItem trackerItem;

    private ImageView mImageView;
    private TextView mTitle, mContent, mStartDate, mEndDate, mDuration, mCommit;

    public static TrackerDetailFragment newInstance(UUID trackerItemId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TRACKER_ID,trackerItemId);
        TrackerDetailFragment fragment = new TrackerDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        UUID mItemId = (UUID) getArguments().getSerializable(EXTRA_TRACKER_ID);
        trackerItem = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackerItem(mItemId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tracker_detail_fragment,null);

        mImageView = (ImageView) view.findViewById(R.id.tracker_detail_image);

        mTitle = (TextView) view.findViewById(R.id.tracker_detail_title);
        mTitle.setText(trackerItem.getmTitle());

        mContent = (TextView) view.findViewById(R.id.tracker_detail_content);
        mContent.setText(trackerItem.getmContent());
        //TODO transform this date to prefer date format.
        mStartDate = (TextView) view.findViewById(R.id.tracker_detail_startDate);
        mStartDate.setText(trackerItem.getStartDate().toString());

        mEndDate = (TextView) view.findViewById(R.id.tracker_detail_endDate);
        mEndDate.setText(trackerItem.getEndDate().toString());
        mDuration = (TextView) view.findViewById(R.id.tracker_detail_totalDuration);
        mCommit = (TextView) view.findViewById(R.id.tracker_detail_commit);

        return view;
    }

//    private class ViewHolder(View view){
//
//    }
}
