package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Frank on 6/28/16.
 */
public class DatePickerDialogFragment extends DialogFragment {
    private Date startDate;
    private Date endDate;

    public static DatePickerDialogFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(Tracker.EXTRA_DATE, date);
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.dialog_fragment_date_picker, null);
        DatePicker datePicker = ((DatePicker) rootView.findViewById(R.id.dialog_date_picker));

        startDate = (Date) getArguments().getSerializable(Tracker.EXTRA_DATE);
//        endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        int year, month, day;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

//        datePicker.setMinDate(startDate.getTime()-1000);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                endDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
            }
        });

        builder.setView(rootView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        return builder.create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent i = new Intent();
        if (endDate!=null) {
            i.putExtra(Tracker.EXTRA_DATE, endDate);
        }else {
            i.putExtra(Tracker.EXTRA_DATE,startDate);
        }
//        LogUtils.d("TAG",i.getExtras());

        getActivity().setResult(Activity.RESULT_OK);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}
