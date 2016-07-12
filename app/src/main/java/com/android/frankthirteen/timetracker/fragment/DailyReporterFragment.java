package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.adapter.DividerItemDecoration;
import com.android.frankthirteen.timetracker.adapter.DurationItemAdapter;
import com.android.frankthirteen.timetracker.db.TrackerDB;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 7/6/16.
 */
public class DailyReporterFragment extends Fragment {

    private static final int REQUEST_DATE = 0x0001;
    private static final String SAVED_DATE = "Date";
    private static final String TAG = "Daily";
    private RecyclerView recyclerView;
    private ImageButton previousDay, nextDay;
    private Date date;
    private Button chooseDate;

    private DurationItemAdapter adapter;

    public static DailyReporterFragment newInstance() {

        Bundle args = new Bundle();

        DailyReporterFragment fragment = new DailyReporterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            date = (Date) savedInstanceState.getSerializable(SAVED_DATE);
        } else {
            date = new Date();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_reporter, container, false);
        previousDay = ((ImageButton) view.findViewById(R.id.di_previous_day));
        nextDay = ((ImageButton) view.findViewById(R.id.di_next_day));
        chooseDate = (Button) view.findViewById(R.id.di_choose_date);

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment =
                        DatePickerDialogFragment.newInstance(new Date());
                datePickerDialogFragment.setTargetFragment(DailyReporterFragment.this, REQUEST_DATE);
                datePickerDialogFragment.show(getFragmentManager(), "Get Date");
            }
        });

        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setTime(date.getTime() - 24 * 60 * 60 * 1000);
                updateDate(date);
            }
        });

        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                updateDate(date);
            }
        });

        updateDate(date);
        adapter = new DurationItemAdapter(getActivity(), getData(date));

        adapter.setOnItemClickListener(new DurationItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                LogUtils.d(TAG, "Duration item id is :" +
                        getData(date).get(position).getId().toString());
            }

            @Override
            public void onLongClick(View v, final int position) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.title_delete_duration_item)
                        .setMessage(R.string.message_delete_duration_item)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeItem(position);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        });

        recyclerView = ((RecyclerView) view.findViewById(R.id.daily_reporter_list));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST);

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void updateDate(Date date) {
        chooseDate.setText(FormatUtils.formatDate(date));

        //1. get new data .
        //2. put it into adapter.
        if (adapter != null) {
            adapter.updateData(getData(date));
            recyclerView.invalidate();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO: 7/6/16 save date info.
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_DATE, date);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_DATE:
                date = (Date) data.getSerializableExtra(Tracker.EXTRA_DATE);
                updateDate(date);
        }
    }

    private List<DurationItem> getData(Date dataDate) {
        List<DurationItem> dailyData = new ArrayList<DurationItem>();
        Calendar c = Calendar.getInstance();
        c.setTime(dataDate);

        int day = c.get(Calendar.DAY_OF_YEAR);
        int year = c.get(Calendar.YEAR);
        dailyData = TrackerDB.getTrackerDB(getActivity()).getDurationItemsByDay(year, day);


        return dailyData;
    }

}
