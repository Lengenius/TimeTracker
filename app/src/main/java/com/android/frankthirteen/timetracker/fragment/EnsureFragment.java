package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.adapter.SpacesItemDecoration;
import com.android.frankthirteen.timetracker.adapter.TagAdapter;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.ArrayList;
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

    private Spinner spinner;
    private RecyclerView tagsView;
    private EditText edContent;
    private int elapsedTime;
    private DurationItem mDurationItem;


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
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.fragment_dialog_ensure, null);

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

        tagsView = ((RecyclerView) view.findViewById(R.id.dialog_ensure_tags));

        final List<String> tags = initialTags();
        final TagAdapter tagsAdapter = new TagAdapter(getActivity(), tags);
        tagsAdapter.setOnItemClickListener(new TagAdapter.OnTagItemClickListener() {
            @Override
            public void onTagItemClick(View v, int position) {
                String tagString = tagsAdapter.getItem(position);
                if (mDurationItem.getTag()!=null){
                    mDurationItem.setTag(tagString);
                    LogUtils.d(TAG,"tagString is :" + mDurationItem.getTag());
                }else{
                    mDurationItem.setTag(tagString);
                    LogUtils.d(TAG,"tagString is set :" + mDurationItem.getTag());
                }
            }
        });
        StaggeredGridLayoutManager tagsManager = new StaggeredGridLayoutManager(tags.size() % 4,
                StaggeredGridLayoutManager.HORIZONTAL);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(16);

        tagsView.setAdapter(tagsAdapter);
        tagsView.setLayoutManager(tagsManager);
        tagsView.addItemDecoration(spacesItemDecoration);

        builder.setView(view);
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);


        return builder.create();
    }

    private List<String> initialTags() {
        List<String> tags = new ArrayList<>();
        tags.add("Study");
        tags.add("Sport");
        tags.add("Entertainment");
        tags.add("Traffic");
        tags.add("Work");
        tags.add("Hobby");


        return tags;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:

                saveDurationItem();
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
        if(mDurationItem!=null){
            TrackerDB.getTrackerDB(getActivity()).insertDurationItem(mDurationItem);
            LogUtils.d(TAG,"saving");
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
