package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.Photo;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Frank on 4/17/16.
 */
public class TrackerDetailFragment extends Fragment {
    public static final String EXTRA_TRACKER_ID = "com.android.frankthirteen.timetracker.trackerItemId";
    private static final String TAG = "TrackerDetailFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TrackerItem trackerItem;

    private ImageButton mImageBtn;
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

        Log.d(TAG, "detail created");

        setDetailPic(view);

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
        //TODO turn int duration into time format.
        mDuration.setText(String.valueOf(trackerItem.getmDuration()));

        mCommit = (TextView) view.findViewById(R.id.tracker_detail_commit);
        mCommit.setText(trackerItem.getmCommit());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
//This is a method to set thumbnail pic;
//                Bundle b = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) b.get("data");
//                mImageView.setImageBitmap(imageBitmap);
//
//                Log.d(TAG, "set pic");
                Photo photo = trackerItem.getmPhoto();
                BitmapDrawable bitmapDrawable = PictureUtils.getScaledPic(getActivity(), photo.getmPhotoPath());
                mImageBtn.setVisibility(View.GONE);
                mImageBtn.setEnabled(false);
                mImageView.setImageDrawable(bitmapDrawable);
//                galleryAddPic();
                break;
            default:
                break;
        }
    }

    private void setDetailPic(View view) {
        mImageView = (ImageView) view.findViewById(R.id.tracker_detail_image);
        //Button visibility may be set somewhere else to avoid collision;
        mImageBtn = (ImageButton) view.findViewById(R.id.tracker_detail_image_button);
        if (trackerItem.getmPhoto()!=null){
            mImageBtn.setVisibility(View.GONE);
        }
        Log.d(TAG,String.valueOf(mImageView.getWidth()));
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                    File mPhotoFile = null;
                    try {
                        Photo photoDetail = new Photo(trackerItem.getmId());
                        trackerItem.setmPhoto(photoDetail);
                        mPhotoFile = trackerItem.getmPhoto().createPhotoFile(photoDetail);
                        Log.d(TAG, "photo created");
                        Log.d(TAG, mPhotoFile.getAbsolutePath());
                    } catch (IOException e) {
                        Log.i(TAG,"file creating failed",e);
                    }
                    if (mPhotoFile != null) {
                        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                    }
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        if (trackerItem.getmPhoto()!=null){
            Photo photo = trackerItem.getmPhoto();
            BitmapDrawable bitmapDrawable = PictureUtils.getScaledPic(getActivity(), photo.getmPhotoPath());
            mImageView.setImageDrawable(bitmapDrawable);
        }
    }

//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(trackerItem.getmPhoto().getmPhotoPath());
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getActivity().sendBroadcast(mediaScanIntent);
//    }
}
