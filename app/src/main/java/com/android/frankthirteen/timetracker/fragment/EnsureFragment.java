package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 6/11/16.
 */
public class EnsureFragment extends android.support.v4.app.DialogFragment implements DialogInterface.OnClickListener {

    private static final String EXTRA_TIME = "com.android.frankthirteen.timetracker.fragment.Extra_Time";

    private Spinner spinner;
    private EditText edContent;
    private int elapsedTime;


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
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.fragment_dialog_ensure, null);
        edContent = ((EditText) view.findViewById(R.id.dialog_ensure_content));
        spinner = ((Spinner) view.findViewById(R.id.dialog_ensure_spinner));
        List<Tracker> trackers = TrackerLab.getTrackerLab(getActivity()).getTrackingTrackers();
        ArrayAdapter<Tracker> adapter = new ArrayAdapter<Tracker>(getActivity(),
                android.R.layout.simple_list_item_1,
                trackers);

        spinner.setAdapter(adapter);

        builder.setView(view);
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);


        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case AlertDialog.BUTTON_POSITIVE:

                sendResult(Activity.RESULT_OK);
                break;
            case AlertDialog.BUTTON_NEGATIVE:

                sendResult(Activity.RESULT_CANCELED);
                break;
            default:

                break;
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
