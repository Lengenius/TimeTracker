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
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Frank on 6/13/16.
 */
public class ReporterChartFragment extends Fragment {

    private static final String TAG = "Chart";

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

        View rootView = inflater.inflate(R.layout.fragment_reporter_chart, container, false);
        chartTitle = (TextView) rootView.findViewById(R.id.reporter_chart_title);
        barChart = (BarChart) rootView.findViewById(R.id.tracker_reporter_barChart);
//set the barChart.
        barChart.setData(initialBarData());
        barChart.setDrawGridBackground(false);

        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();


        yAxisLeft.setAxisMinValue(0);
//        yAxisLeft.setAxisMaxValue(1200);
        yAxisLeft.setLabelCount(6, false);
        yAxisLeft.setValueFormatter(new YValueFormatter());

        yAxisRight.setEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);

        barChart.setVisibleXRangeMinimum(7);
        barChart.setScaleYEnabled(false);
        barChart.setVisibleXRangeMaximum(7);

        barChart.invalidate();

        chartTitle.setText(mTracker.getTitle());

        return rootView;
    }

    //    Should I put this method into DataBase to reuse it?
    private BarData initialBarData() {
        List<DurationItem> rawData;
        List<BarEntry> wrappedData = new ArrayList<BarEntry>();
        List<String> xVals = new ArrayList<String>();
//        mTracker.setDurationItems(
//                TrackerDB.getTrackerDB(getActivity()).getDurationItemsByTracker(mTracker)
//        );


        if (mTracker.getDurationItems().size() != 0) {
            rawData = mTracker.getDurationItems();

            prepareXLabels(rawData, wrappedData, xVals);

            int period = 0;
            int diDay = 0;
            double dayTime = 0;

            for (int i = 0; i < rawData.size(); i++) {
//                get the item in list.
                DurationItem di = rawData.get(i);
                if (diDay == 0) {
//                    get the 1st item and store its day & duration info.
                    diDay = di.getDay();
                    period = di.getDuration();
                    dayTime = di.getDate().getTime();

//                    Calendar c = Calendar.getInstance();
//                    c.setTime(di.getDate());
//                    weekDay = c.get(Calendar.DAY_OF_WEEK);
                } else if (diDay == di.getDay()) {
//                    if the next item is added in the same day.
//                    sum its durations info.
                    period += di.getDuration();
                    dayTime = di.getDate().getTime();
                } else {
//                    new Entry Item. which means new day.
//                    if it is a duration item in another day. add the stored info to an entry.
//                    dayIndex should also related to di's weekday.

                    double trackerStarDate = mTracker.getStartDate().getTime();

                    int dayIndex = (int) Math.floor((dayTime - trackerStarDate)
                            / 1000.0 / 60 / 60 / 24);
//                    turn period in second into minutes.
                    BarEntry entry = new BarEntry(period / 60, dayIndex);
                    wrappedData.set(dayIndex, entry);
                    dayTime = di.getDate().getTime();

//                    refresh the stored info.
                    diDay = di.getDay();
                    period = di.getDuration();
                }
//                When reached the last item.
                if (i == rawData.size() - 1) {
                    int dayIndex = (int) Math.floor((
                            di.getDate().getTime() - mTracker.getStartDate().getTime())
                            / 1000.0 / 60 / 60 / 24);
                    BarEntry entry = new BarEntry(period/60, dayIndex);
                    wrappedData.set(dayIndex, entry);
                }

            }
        }

        BarDataSet barDataSet = new BarDataSet(wrappedData, mTracker.getTitle());


        return new BarData(xVals, barDataSet);

    }

    private void prepareXLabels(List<DurationItem> rawData, List<BarEntry> wrappedData, List<String> xVals) {
        long lastItem = rawData.get(rawData.size() - 1).getDate().getTime();
        DurationItem firstItem = rawData.get(0);
        int firstDayOfWeek = FormatUtils.getDayOfWeek(firstItem.getDate());

        int daySinceStart = (int) Math.ceil(((lastItem - mTracker.getStartDate().getTime())) /
                1000 / 60.0 / 60.0 / 24.0);

        for (int i = 0; i < daySinceStart + 1; i++) {
            BarEntry barEntry = new BarEntry(0, i);
            wrappedData.add(i, barEntry);
            //Since the first day is sunday.
            xVals.add(getXLabel(firstDayOfWeek - 1 + i));
        }
    }

    private String getXLabel(int i) {
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        String[] dayNames = symbols.getShortWeekdays();
        int returnType = 0;
        switch (returnType) {
            case 0:
                String result = dayNames[i % 7 + 1];
                LogUtils.d(TAG, "item sequence is " + i % 7 + result);

                return result;
            default:
                return null;
        }

    }

    class YValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;

        public YValueFormatter() {
            mFormat = new DecimalFormat("###,##0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(value) + " Mins";
        }
    }

//    private void fakeData() {
//        Random r = new Random();
//        r.setSeed(2);
//
//        List<DurationItem> durationItems = mTracker.getDurationItems();
//        for (int i = 0; i < 14; i++) {
//            if (i % 7 != 0) {
//                DurationItem di = new DurationItem();
//                di.setTrackerId(mTracker.getId());
//                di.setId(UUID.randomUUID());
//                Date diDate = new Date();
//                long startDate = mTracker.getStartDate().getTime();
//                diDate.setTime(startDate + i * 1000 * 60 * 60 * 24 - 1000);
//                di.setDate(diDate);
//                LogUtils.d(TAG, di.getDay() + " days since start");
//                di.setDuration(r.nextInt(1400));
//                durationItems.add(di);
//            } else {
//                DurationItem di = new DurationItem();
//                di.setTrackerId(mTracker.getId());
//                di.setId(UUID.randomUUID());
//                Date diDate = new Date();
//                long startDate = mTracker.getStartDate().getTime();
//                diDate.setTime(startDate + i * 1000 * 60 * 60 * 24);
//                di.setDate(diDate);
//                LogUtils.d(TAG, di.getDay() + " days since start");
//                di.setDuration(0);
//                durationItems.add(di);
//            }
//        }
//
//        LogUtils.d(TAG, mTracker.getDurationItems().size() + " items.");
//    }

}
