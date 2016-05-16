package com.android.frankthirteen.timetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.service.PlaySounds;
import com.android.frankthirteen.timetracker.utils.FormatUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by Frank on 5/5/16.
 */
public class WorkFocusFragment extends Fragment {
    private static final String TAG = "WORKFOCUS";

    private static Handler mHandler;
    private static final int UPDATE_CLOCK = 0;
    private static final int STOP_CLOCK = 1;

    private TrackerItem mTrackerItem;
    private TextView workFocusTitle, workFocusContent, workFocusClock;
    private Button btnStart, btnPause;

    /**
     * The only way to get a WorkFocusFragment
     *
     * @param trackerItemId the Unique id for a tracker item, leads to the other information of it.
     * @return
     */
    public static WorkFocusFragment newInstance(UUID trackerItemId) {
        Log.d(TAG, "new Instance");

        Bundle args = new Bundle();
        args.putSerializable(TrackerDetailFragment.EXTRA_TRACKER_ID, trackerItemId);
        WorkFocusFragment fragment = new WorkFocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate.");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        UUID trackerItemId = (UUID) getArguments().getSerializable(TrackerDetailFragment.EXTRA_TRACKER_ID);
        mTrackerItem = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackerItem(trackerItemId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_work_focus, null);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_CLOCK:
                        workFocusClock.setText(FormatUtils.formatDuration(mTrackerItem.getmDuration()));
                        break;
                    case STOP_CLOCK:
                        break;
                    default:
                        break;
                }
            }
        };

        workFocusContent = (TextView) rootView.findViewById(R.id.workFocus_content);
        workFocusContent.setText(mTrackerItem.getmContent());
        workFocusTitle = (TextView) rootView.findViewById(R.id.workFocus_title);
        workFocusTitle.setText(mTrackerItem.getmTitle());
        workFocusClock = (TextView) rootView.findViewById(R.id.workFocus_Clock);
        workFocusClock.setText(FormatUtils.formatDuration(mTrackerItem.getmDuration()));
        btnStart = (Button) rootView.findViewById(R.id.workFocus_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClock();
                setButton();
            }
        });
        btnPause = (Button) rootView.findViewById(R.id.workFocus_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseClock();
                btnStart.setEnabled(true);
            }
        });
        setButton();

        return rootView;
    }

    private void startClock() {
        mTrackerItem.setmIsStarted(true);
        mHandler.post(new WorkFocusTimer());
        Intent intent = new Intent(getActivity(), PlaySounds.class);
        getActivity().startService(intent);

    }

    private void pauseClock() {
        mTrackerItem.setmIsStarted(false);
        mTrackerItem.saveDuration();
        Intent intent = new Intent(getActivity(),PlaySounds.class);
        getActivity().stopService(intent);

    }

    private void setButton(){
        if (mTrackerItem.isStarted()){
            btnStart.setEnabled(false);
        }else {
            btnStart.setEnabled(true);
        }
    }

    private class WorkFocusTimer implements Runnable {
        @Override
        public void run() {
            if (mTrackerItem.isStarted()) {
                mTrackerItem.increase();
                Message message = Message.obtain(mHandler,UPDATE_CLOCK);
                message.sendToTarget();
                //if you move the handler post outside if, it will create multi thread.
                mHandler.postDelayed(this, 10);
            }
        }
    }

}
