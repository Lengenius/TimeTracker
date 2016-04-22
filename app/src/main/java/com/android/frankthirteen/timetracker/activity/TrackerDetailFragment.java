package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
    public static final String EXTRA_TRACKER_ID = "com.android.frankthirteen.timetracker.trackerItemId";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TrackerItem trackerItem;

    private ImageView mImageView;
    private TextView mTitle, mContent, mStartDate, mEndDate, mDuration, mCommit;

    public static TrackerDetailFragment newInstance(UUID trackerItemId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TRACKER_ID, trackerItemId);
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
        View view = inflater.inflate(R.layout.tracker_detail_fragment, null);

        mImageView = (ImageView) view.findViewById(R.id.tracker_detail_image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager())!=null) {
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        //TODO get camera to save photo.

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
        mDuration.setText(""+trackerItem.getmDuration());

        mCommit = (TextView) view.findViewById(R.id.tracker_detail_commit);
        mCommit.setText(trackerItem.getmCommit());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK) return;
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                Bundle b =data.getExtras();
                Bitmap imageBitmap = (Bitmap) b.get("data");
                mImageView.setImageBitmap(imageBitmap);
                break;
            default:
                break;
        }
    }
}
