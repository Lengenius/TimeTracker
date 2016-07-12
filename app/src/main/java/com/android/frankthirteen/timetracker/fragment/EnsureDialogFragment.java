package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 6/11/16.
 */
public class EnsureDialogFragment extends android.support.v4.app.DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String EXTRA_TIME =
            "com.android.frankthirteen.timetracker.fragment.Extra_Time";
    private static final String TAG = "EnsureDialogFragment";
    private static final int REQUEST_TRACKER = 0x0000;
    private Context mContext;

    public String[] tagsValues;

    private Button btnChooseTracker;
    private EditText edContent;
    private int elapsedTime;
    private DurationItem mDurationItem;
    private TagFlowLayout tagsContainer;
    private LayoutInflater layoutInflater;

    private UUID trackerId;


    public static EnsureDialogFragment newInstance(int elapsedTime) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_TIME, elapsedTime);
        EnsureDialogFragment fragment = new EnsureDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        tagsValues = new String[]{
                mContext.getString(R.string.tag_sport),
                mContext.getString(R.string.tag_Entertainment),
                mContext.getString(R.string.tag_work),
                mContext.getString(R.string.tag_traffic),
                mContext.getString(R.string.tag_study),
                mContext.getString(R.string.tag_hobby),
                mContext.getString(R.string.tag_rest)};
        if (getArguments().getInt(EXTRA_TIME) != 0) {
            elapsedTime = getArguments().getInt(EXTRA_TIME);
            mDurationItem = new DurationItem(getActivity());
            mDurationItem.setDuration(elapsedTime);
        }
        layoutInflater = LayoutInflater.from(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog_fragment_ensure, null);

        tagsContainer = ((TagFlowLayout) view.findViewById(R.id.dialog_ensure_tags));
        initialTags();

        edContent = ((EditText) view.findViewById(R.id.dialog_ensure_comment));

        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mDurationItem.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        spinner = ((Spinner) view.findViewById(R.id.dialog_ensure_spinner));
//        List<Tracker> trackers = TrackerLab.getTrackerLab(getActivity()).getTrackingTrackers();
//        final ArrayAdapter<Tracker> adapter = new ArrayAdapter<Tracker>(getActivity(),
//                android.R.layout.simple_list_item_1,
//                trackers);
//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                trackerId = ((Tracker) parent.getItemAtPosition(position)).getId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnChooseTracker = ((Button) view.findViewById(R.id.choose_tracker));
        btnChooseTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackerChooseFragment chooseTracker = TrackerChooseFragment.newInstance();
                chooseTracker.setTargetFragment(EnsureDialogFragment.this,REQUEST_TRACKER);
                chooseTracker.show(getFragmentManager(),"Choose Tag");
                chooseTracker.setCancelable(false);
            }
        });


        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);


        return builder.create();
    }

    private void initialTags() {
        tagsContainer.setAdapter(new TagAdapter<String>(tagsValues) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tag = (TextView) layoutInflater.inflate(R.layout.tag_view, parent, false);
                tag.setText(tagsValues[position]);
                return tag;
            }
        });

        tagsContainer.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mDurationItem.setTag(position);
                return true;
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:
                if (trackerId != null) {
                    TrackerLab.getTrackerLab(getActivity()).getTracker(trackerId).addDuration(mDurationItem);
                }
                saveDurationItem();
//                The order matters.
//                LogUtils.d(TAG,"Duration item tracker id" + mDurationItem.getTrackerId().toString());
                sendResult(Activity.RESULT_OK);
                break;
            case AlertDialog.BUTTON_NEGATIVE:

                sendResult(Activity.RESULT_CANCELED);
                break;
            default:

                break;
        }
    }

    private void saveDurationItem() {
        if (mDurationItem != null) {
            mDurationItem.setComment(edContent.getText().toString());
            mDurationItem.setTrackerId(trackerId);
            TrackerDB.getTrackerDB(getActivity()).insertDurationItem(mDurationItem);
            LogUtils.d(TAG, "saving");
        }
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode!=Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQUEST_TRACKER:
                trackerId = (UUID) data.getSerializableExtra(Tracker.EXTRA_ID);
                String trTitle = data.getStringExtra(Tracker.TITLE);
                btnChooseTracker.setText(trTitle);
                break;
            default:

                break;

        }
    }
}
