package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.activity.TrackerDetailActivity;
import com.android.frankthirteen.timetracker.adapter.ItemListAdapter;
import com.android.frankthirteen.timetracker.model.TrackerItem;
import com.android.frankthirteen.timetracker.model.TrackerItemLab;
import com.android.frankthirteen.timetracker.utils.LogUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Frank on 4/10/16.
 */
public class TrackerListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    public static final int REQUEST_DETAIL = 1;
    public static final int REQUEST_DURATION = 2;

    private List<TrackerItem> trackerItems;
    private ItemListAdapter itemListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerItems = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackingItems();
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trackerlist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_tracker:
                createNewTracker();
                return true;
            case R.id.menu_item_delete:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tracker_list_container, container, false);
        final ListView trackerListView = (ListView) rootView.findViewById(R.id.tracker_listView);

        itemListAdapter = new ItemListAdapter(this, getActivity(), R.layout.cell_tracker, trackerItems);
        trackerListView.setAdapter(itemListAdapter);
        trackerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG, "list item clicked");
            }
        });


        //The choice mode mattered.
        trackerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        trackerListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_menu_delete:
                        TrackerItemLab trackerItemLab = TrackerItemLab.getsTrackerItemLab(getContext());
                        for (int i = itemListAdapter.getCount() + 1; i >= 0; i--) {
                            if (trackerListView.isItemChecked(i)) {
                                LogUtils.d(TAG, i + "");
                                trackerItemLab.deleteTrackerItem(itemListAdapter.getItem(i));
                                //Also need to remove item from adapter. But why?
                                itemListAdapter.remove(itemListAdapter.getItem(i));
                            }
                        }
                        itemListAdapter.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    default:
                        return true;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO bug when device is rotated.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            LogUtils.d(TAG, "result ok");
            switch (requestCode) {
                case REQUEST_DETAIL:
                    itemListAdapter.notifyDataSetChanged();
                    LogUtils.d(TAG, "there items " + itemListAdapter.getCount());
                    UUID resultID = (UUID) data.getSerializableExtra(TrackerDetailFragment.EXTRA_TRACKER_ID);
                    TrackerItem refreshItem = TrackerItemLab.getsTrackerItemLab(getActivity()).getTrackerItem(resultID);
                    TrackerItemLab.getsTrackerItemLab(getActivity()).saveTrackerItem(refreshItem);
                    if (refreshItem.getmPhoto() != null) {
                        LogUtils.d(TAG, "photo saved to:" + refreshItem.getmPhoto().getmPhotoPath());
                    }
                    break;
                case REQUEST_DURATION:
                    break;
                default:
                    break;
            }
        }
    }


    private void createNewTracker() {
        TrackerItem item = new TrackerItem();
        TrackerItemLab.getsTrackerItemLab(getActivity()).addTrackItem(item);
        trackerItems.add(item);
        Intent intent = new Intent(getActivity(), TrackerDetailActivity.class);
        intent.putExtra(TrackerDetailFragment.EXTRA_TRACKER_ID, item.getmId());
        startActivityForResult(intent, REQUEST_DETAIL);
    }

}
