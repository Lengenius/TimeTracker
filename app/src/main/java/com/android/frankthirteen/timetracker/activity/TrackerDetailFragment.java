package com.android.frankthirteen.timetracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.DurationItem;
import com.android.frankthirteen.timetracker.model.Photo;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.PictureUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Frank on 4/17/16.
 */
public class TrackerDetailFragment extends Fragment {
    public static final String EXTRA_TRACKER_ID = "com.android.frankthirteen.timetracker.trackerItemId";
    private static final String TAG = "TrackerDetailFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CROP = 2;
    private TrackerItem trackerItem;

    private ImageButton mImageBtn;
    private ImageView mImageView;
    private EditText mTitle, mContent, mCommit;
    private TextView mDuration;
    private Button mStartDate,mEndDate;
    private LineChart detailLineChart;

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

        detailLineChart = (LineChart) view.findViewById(R.id.detail_chart);
        detailLineChart.setData(buildLineChart());

        mTitle = (EditText) view.findViewById(R.id.tracker_detail_title);
        mTitle.setText(trackerItem.getmTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                trackerItem.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                //
            }
        });

        mContent = (EditText) view.findViewById(R.id.tracker_detail_content);
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                trackerItem.setmContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mContent.setText(trackerItem.getmContent());
        //TODO transform this date to preferred date format.
        mStartDate = (Button) view.findViewById(R.id.tracker_detail_startDate);
        mStartDate.setText(trackerItem.getStartDate().toString());

        mEndDate = (Button) view.findViewById(R.id.tracker_detail_endDate);
        mEndDate.setText(trackerItem.getEndDate().toString());

        mDuration = (TextView) view.findViewById(R.id.tracker_detail_totalDuration);
        //TODO turn int duration into time format.
        mDuration.setText(String.valueOf(trackerItem.getmDuration()));

        mCommit = (EditText) view.findViewById(R.id.tracker_detail_commit);
        mCommit.setText(trackerItem.getmCommit());
        mCommit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                trackerItem.setmCommit(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                Photo photo = trackerItem.getmPhoto();
                BitmapDrawable bitmapDrawable = PictureUtils.getScaledPic(getActivity(), photo.getmPhotoPath());
                mImageBtn.setVisibility(View.GONE);
                mImageBtn.setEnabled(false);
                mImageView.setImageDrawable(bitmapDrawable);
                break;
            case REQUEST_IMAGE_CROP:
                //TODO crop the photo to fit the screen and decrease its size.


                break;
            default:
                break;
        }
    }

    private void setDetailPic(View view) {
        mImageView = (ImageView) view.findViewById(R.id.tracker_detail_image);
        mImageBtn = (ImageButton) view.findViewById(R.id.tracker_detail_image_button);
        if (trackerItem.getmPhoto() != null) {
            mImageBtn.setVisibility(View.GONE);
        }
        Log.d(TAG, String.valueOf(mImageView.getWidth()));
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
                        Log.i(TAG, "file creating failed", e);
                    }
                    if (mPhotoFile != null) {
                        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                    }
                    startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        if (trackerItem.getmPhoto() != null) {
            Photo photo = trackerItem.getmPhoto();
            BitmapDrawable bitmapDrawable = PictureUtils.getScaledPic(getActivity(), photo.getmPhotoPath());
            mImageView.setImageDrawable(bitmapDrawable);
        }
    }

    private LineData buildLineChart() {
        ArrayList<Entry> valsItem = new ArrayList<Entry>();
        prepareFakeData();
        List<DurationItem> durationItems = trackerItem.getmDurationItems();
        ArrayList<String> xVals = new ArrayList<String>();
        int x = 0;
        for (DurationItem dItem :
                durationItems) {
            Entry data = new Entry(dItem.getmDuration(), x++);
            xVals.add(String.valueOf(dItem.getDay()));
            valsItem.add(data);
        }
        LineDataSet trackedActivity = new LineDataSet(valsItem, trackerItem.getmTitle());
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(trackedActivity);

        return new LineData(xVals, dataSets);
    }

    private void prepareFakeData() {
        Date mDate = trackerItem.getStartDate();
        List<DurationItem> fakeDuration = trackerItem.getmDurationItems();

        Calendar calendar = GregorianCalendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        Random r = new Random();
        r.setSeed(0);

        calendar.setTime(mDate);

        for (int i = 0; i < 5; i++) {
            Date fakeDate = new GregorianCalendar(year,month,day++).getTime();
            DurationItem di = new DurationItem(fakeDate, r.nextInt(),trackerItem.getmId());
            fakeDuration.add(di);
        }
    }

}
