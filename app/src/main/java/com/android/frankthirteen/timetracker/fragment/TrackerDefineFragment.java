package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Frank on 6/28/16.
 */
public class TrackerDefineFragment extends Fragment {

    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_PHOTO = 1;

    private static final String TAG = "TrackerDefineFragment";

    private UUID mId;
    private EditText trTitle, trContent, trComment, trTimeCost;
    private TextView trEndDate;
    private ImageButton iBtnTakePhoto;
    private Button btnChooseDate;
    private Tracker tracker;

    public static TrackerDefineFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(Tracker.EXTRA_ID, id);
        TrackerDefineFragment fragment = new TrackerDefineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = ((UUID) getArguments().getSerializable(Tracker.EXTRA_ID));
        TrackerLab trackerLab = TrackerLab.getTrackerLab(getActivity());
        if (TrackerLab.getTrackerLab(getActivity()).getTracker(mId) == null) {
            trackerLab.addTracker(new Tracker(mId));
        }
        tracker = TrackerLab.getTrackerLab(getActivity()).getTracker(mId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //without the attachToRoot value, there will be an IllegalStateException: The specified child
        //already has a parent. You must call removeView() on the child's parent first.
        View rootView = inflater.inflate(R.layout.fragment_tracker_define, container, false);
        initialView(rootView);

        trTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trTimeCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setPlanningTime(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "end date button clicked.");
                //set date picker fragment include target fragment,
                DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(tracker.getEndDate());
                datePicker.setTargetFragment(TrackerDefineFragment.this, REQUEST_DATE);
                datePicker.show(getFragmentManager(), "Date");


            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case REQUEST_DATE:
                Date date = (Date) data.getSerializableExtra(Tracker.EXTRA_DATE);
// TODO: 6/28/16 set a good look format;
                SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance();

                tracker.setEndDate(date);
                trEndDate.setText(date.toString());
                break;
            case REQUEST_PHOTO:

                break;
        }
    }

    private void initialView(View rootView) {
        trTitle = (EditText) rootView.findViewById(R.id.tracker_title);
        trContent = (EditText) rootView.findViewById(R.id.tracker_content);
        trComment = (EditText) rootView.findViewById(R.id.tracker_comment);
        trTimeCost = (EditText) rootView.findViewById(R.id.tracker_plan_time);

        trEndDate = (TextView) rootView.findViewById(R.id.tracker_end_date);

        iBtnTakePhoto = (ImageButton) rootView.findViewById(R.id.tracker_add_photo);
        btnChooseDate = (Button) rootView.findViewById(R.id.tracker_choose_date);

    }

}
