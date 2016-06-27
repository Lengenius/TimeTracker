package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
public class EnsureFragment extends android.support.v4.app.DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String EXTRA_TIME =
            "com.android.frankthirteen.timetracker.fragment.Extra_Time";
    private static final String TAG = "EnsureFragment";

    public String[] tagsValues = new String[]{
                    "Sport", "Entertainment", "Work", "Traffic", "Study", "Hobby"};

    private Spinner spinner;
    private EditText edContent;
    private int elapsedTime;
    private DurationItem mDurationItem;
    private TagFlowLayout tagsContainer;
    private LayoutInflater layoutInflater;


    public static EnsureFragment newInstance(int elapsedTime) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_TIME, elapsedTime);
        EnsureFragment fragment = new EnsureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elapsedTime = getArguments().getInt(EXTRA_TIME);
        mDurationItem = new DurationItem(getActivity());
        mDurationItem.setDuration(elapsedTime);
        layoutInflater = LayoutInflater.from(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.fragment_dialog_ensure, null);

        tagsContainer = ((TagFlowLayout) view.findViewById(R.id.dialog_ensure_tags));
        initialTags();

        edContent = ((EditText) view.findViewById(R.id.dialog_ensure_content));

        spinner = ((Spinner) view.findViewById(R.id.dialog_ensure_spinner));
        List<Tracker> trackers = TrackerLab.getTrackerLab(getActivity()).getTrackingTrackers();
        final ArrayAdapter<Tracker> adapter = new ArrayAdapter<Tracker>(getActivity(),
                android.R.layout.simple_list_item_1,
                trackers);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UUID trackerId = ((Tracker) parent.getItemAtPosition(position)).getId();
                mDurationItem.setTrackerId(trackerId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setView(view);
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);


        return builder.create();
    }

    private void initialTags() {
        tagsContainer.setAdapter(new TagAdapter<String>(tagsValues) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tag = (TextView) layoutInflater.inflate(R.layout.tag_view,parent,false);
                tag.setText(tagsValues[position]);

                return tag;
            }
        });

        tagsContainer.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mDurationItem.setTag(tagsValues[position]);
                Toast.makeText(getActivity(), tagsValues[position]+" clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:

                saveDurationItem();
                sendResult(Activity.RESULT_OK);
                mDurationItem.setComment(edContent.getText().toString());
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

}
