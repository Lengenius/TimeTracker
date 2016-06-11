package com.android.frankthirteen.timetracker.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public static DetailFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(),"Detail begin",Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_tracker_detail_content, container, false);
    }

}
