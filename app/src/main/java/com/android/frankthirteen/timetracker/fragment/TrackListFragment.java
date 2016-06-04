package com.android.frankthirteen.timetracker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.adapter.RecyclerViewAdapter;

/**
 * Created by Frank on 5/26/16.
 */
public class TrackListFragment extends Fragment {

    public static TrackListFragment newInstance() {

        Bundle args = new Bundle();

        TrackListFragment fragment = new TrackListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_tracker, null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.tracker_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(getActivity(), "item long clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;

    }
}
