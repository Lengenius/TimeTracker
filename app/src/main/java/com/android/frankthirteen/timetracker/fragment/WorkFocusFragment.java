package com.android.frankthirteen.timetracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;


/**
 * Created by Frank on 5/24/16.
 */
public class WorkFocusFragment extends Fragment implements View.OnClickListener {
    public static WorkFocusFragment newInstance() {

        Bundle args = new Bundle();
//        UUID mId = id;
//        args.putSerializable();
        WorkFocusFragment fragment = new WorkFocusFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.work_focus_fragment,null);
        TextView timer = (TextView) rootView.findViewById(R.id.work_focus_timer);
        Button btnStart = (Button) rootView.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
        Button btnPause = (Button) rootView.findViewById(R.id.btn_pause);
        btnPause.setOnClickListener(this);



        return rootView;

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_start:
                startTimer();
                break;
            case R.id.btn_pause:
                pauseTimer();
                break;
        }
    }

    /**TODO
     * stop a timer service and add the duration to a tracking tracker.
     * popup some buttons which means you can make some notes of this time.
     */
    private void pauseTimer() {

    }

    /**
     * start a timer service and change the UI(Button appearance) of this.
     */
    private void startTimer() {

    }


}
