package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;


/**
 * Created by Frank on 5/24/16.
 */
public class WorkFocusFragment extends Fragment implements View.OnClickListener {
    public static final int UPDATE_TIMER = 0;
    public static final int STOP_TIMER = 1;
    private static final String TAG = "WorkFocusFragment";
    private static final String ADD_TO = "Add to Tracker";
    private static final int REQUEST_TRACKER = 0;

    private TextView timerTv;
    private Button btnStart, btnStop;
    private boolean started = false;
    private int elapsedTime = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIMER:

                    updateTimer();
                    break;
                case STOP_TIMER:

                    break;
            }
        }
    };

    public static WorkFocusFragment newInstance() {

        Bundle args = new Bundle();
        WorkFocusFragment fragment = new WorkFocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_work_focus, null);
        timerTv = (TextView) rootView.findViewById(R.id.work_focus_timer);
        btnStart = (Button) rootView.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        btnStop = (Button) rootView.findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startTimer();
                LogUtils.d(TAG, "button clicked");
                setButtonState();
                break;
            case R.id.btn_stop:
                stopTimer();
                setButtonState();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            elapsedTime = 0;
            updateTimer();
            return;
        }
        switch (requestCode) {
            case REQUEST_TRACKER:

                Toast.makeText(getActivity(),"Duration added",Toast.LENGTH_SHORT).show();
                elapsedTime = 0;
                break;
            default:

                break;
        }
        LogUtils.d(TAG, "elapsedTime is " + elapsedTime + "now");
        updateTimer();
    }

    /**
     * stop a timer service and add the duration to a tracking tracker.
     * popup some buttons which means you can make some notes of this time.
     */
    private void stopTimer() {
        started = false;
        DialogFragment dialog = EnsureDialogFragment.newInstance(elapsedTime);
        dialog.setTargetFragment(WorkFocusFragment.this, REQUEST_TRACKER);
        dialog.show(getFragmentManager(), ADD_TO);

    }


    /**
     * start a timer service and change the UI(Button appearance) of this.
     */
    private void startTimer() {
        started = true;
        mHandler.post(new TimerThread());
        LogUtils.d(TAG, "start timer method");
    }

    private void setButtonState() {
        if (started) {
            btnStart.setEnabled(false);
            btnStop.setVisibility(View.VISIBLE);
        } else {
            btnStop.setVisibility(View.GONE);
            btnStart.setEnabled(true);
        }
    }

    private void updateTimer() {
        timerTv.setText(FormatUtils.formatTime(elapsedTime));
    }

    class TimerThread implements Runnable {

        @Override
        public void run() {

            if (started) {
                elapsedTime++;
                Message msg = Message.obtain(mHandler, WorkFocusFragment.UPDATE_TIMER);
                msg.sendToTarget();
                mHandler.postDelayed(this, 10);
            }

        }
    }
}
