package com.android.frankthirteen.timetracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.DurationItem;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterChartFragment extends Fragment {

    private Tracker mTracker;
    private BarChart barChart;
    private TextView chartTitle;

    public static ReporterChartFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();

        args.putSerializable(Tracker.EXTRA_ID, uuid);
        ReporterChartFragment fragment = new ReporterChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReporterChartFragment() {
        //Constructor

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UUID id = (UUID) getArguments().getSerializable(Tracker.EXTRA_ID);
        TrackerLab trackerLab = TrackerLab.getTrackerLab(getActivity());
        mTracker = trackerLab.getTracker(id);

        View rootView = inflater.inflate(R.layout.fragment_reporter_chart, null);
        chartTitle = (TextView) rootView.findViewById(R.id.reporter_chart_title);
        barChart = (BarChart) rootView.findViewById(R.id.tracker_reporter_barChart);

        barChart.setData(initialBarData());
        barChart.invalidate();

        chartTitle.setText(mTracker.getTitle());

        return rootView;
    }

    private BarData initialBarData() {
        List<DurationItem> rawData;
        List<BarEntry> wrappedData = new ArrayList<BarEntry>();
        List<String> xVals = new ArrayList<String>();
        if (mTracker.getDurationItems() != null) {
            rawData = mTracker.getDurationItems();
            int duration = 0;
            int day = 0;
            int index = 0;
            for (int i = 0; i < rawData.size(); i++) {
//                get the item in list.
                DurationItem di = rawData.get(i);
                if (day == 0) {
//                    get the 1st item and store its day & duration info.
                    day = di.getDay();
                    duration = di.getDuration();
                } else if (day == di.getDay()) {
//                    if the next item is added in the same day.
//                    sum its duration info.
                    duration += di.getDuration();
                } else {
//                    if it is a duration item in another day. add the stored info to an entry.
                    BarEntry entry = new BarEntry(duration, index);
                    String xLabel;
                    wrappedData.add(entry);
                    xLabel = getXLabel(di);
                    xVals.add(xLabel);
//                    refresh the stored info.
                    day = di.getDay();
                    index++;
                    duration = di.getDuration();
                }
//                When reached the last item.
                if (i == rawData.size() - 1) {
                    BarEntry entry = new BarEntry(duration, index);
                    String xLabel;
                    wrappedData.add(entry);
                    xLabel = getXLabel(di);
                    xVals.add(xLabel);
                }

            }
        }

        BarDataSet barDataSet = new BarDataSet(wrappedData, mTracker.getTitle());

        return new BarData(xVals, barDataSet);

    }

    private String getXLabel(DurationItem di) {
        Date date = di.getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return different xLabel on condition.
        int returnType = 0;
        switch (returnType) {
            case 0:
                return c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            default:
                return null;
        }

    }

}
