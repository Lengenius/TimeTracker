package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.adapter.DividerItemDecoration;
import com.android.frankthirteen.timetracker.adapter.TrackerTitleAdapter;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;

import java.util.List;

/**
 * Created by Frank on 7/12/16.
 */
public class TrackerChooseFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private Context mContext;
    private List<Tracker> trackers;

    public static TrackerChooseFragment newInstance() {

        Bundle args = new Bundle();

        TrackerChooseFragment fragment = new TrackerChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =getActivity();
        trackers = TrackerLab.getTrackerLab(mContext).getTrackingTrackers();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.dialog_tracker_choose, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.tracker_choose_rv);

        TrackerTitleAdapter adapter = new TrackerTitleAdapter(mContext, trackers);
        adapter.setOnItemClickListener(new TrackerTitleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra(Tracker.EXTRA_ID, trackers.get(position).getId());
                intent.putExtra(Tracker.TITLE, trackers.get(position).getTitle());
                sendResult(intent);
                dismiss();
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        });


        LinearLayoutManager linearManager = new LinearLayoutManager(mContext
                , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapter(adapter);

        builder.setView(view);
        builder.setTitle(R.string.dialog_title_choose_tracker);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    private void sendResult(Intent i) {


        getActivity().setResult(Activity.RESULT_OK);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }


}
