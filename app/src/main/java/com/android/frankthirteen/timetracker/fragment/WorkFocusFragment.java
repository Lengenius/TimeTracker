package com.android.frankthirteen.timetracker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

    private TextView timerTv;
    private Button btnStart, btnStop;
    private boolean started = false;
    private int elapsedTime = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIMER:

                    timerTv.setText(FormatUtils.timerFormat(elapsedTime));

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
        View rootView = inflater.inflate(R.layout.work_focus_fragment, null);
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
                buttonSet();
                break;
            case R.id.btn_stop:
                stopTimer();
                buttonSet();
                break;
        }
    }

    /**
     * TODO
     * stop a timer service and add the duration to a tracking tracker.
     * popup some buttons which means you can make some notes of this time.
     */
    private void stopTimer() {
        started = false;

        //TODO save elapsed time to a certain tracker. reset elapsed time. stop related service.
    }


    /**
     * start a timer service and change the UI(Button appearance) of this.
     */
    private void startTimer() {
        started = true;
        mHandler.post(new TimerThread());
        LogUtils.d(TAG, "start timer method");
    }
    
    private void buttonSet() {
        if (started) {
            btnStart.setEnabled(false);
            btnStop.setVisibility(View.VISIBLE);
            //TODO set button state when stopwatch running;
        } else {
            //TODO set button state when stopwatch stopped;
            btnStop.setVisibility(View.GONE);
            btnStart.setEnabled(true);
        }
    }

    class TimerThread implements Runnable {

        @Override
        public void run() {

            if (started) {
                LogUtils.d(TAG, "inside a thread");
                elapsedTime++;
                Message msg = Message.obtain(mHandler, WorkFocusFragment.UPDATE_TIMER);
                msg.sendToTarget();
                //TODO time interval will be set to 1000 when upload to store.
                mHandler.postDelayed(this, 10);
            }

        }
    }
}
