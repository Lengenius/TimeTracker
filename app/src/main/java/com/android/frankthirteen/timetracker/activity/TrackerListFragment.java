package com.android.frankthirteen.timetracker.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.utils.TrackerItem;
import com.android.frankthirteen.timetracker.utils.TrackerItemAdapter;
import com.android.frankthirteen.timetracker.utils.TrackerItemLab;

import java.util.ArrayList;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tracker_list_container, container, false);
        ListView trackerListView = (ListView) rootView.findViewById(R.id.tracker_listview);
        ArrayList<TrackerItem> trackerItems = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackerItems();
        trackerItems.add(new TrackerItem());
        TrackerItemAdapter trackerItemAdapter = new TrackerItemAdapter(getActivity(),R.layout.listitem_tracker,trackerItems);
        trackerListView.setAdapter(trackerItemAdapter);
        return rootView;
    }
}
