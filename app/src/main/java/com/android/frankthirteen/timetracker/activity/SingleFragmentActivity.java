package com.android.frankthirteen.timetracker.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.android.frankthirteen.timetracker.R;


/**
 * Created by Frank on 4/10/16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    /**
     * get the Activity a fragment to hold;
     * @return fragment
     */
    protected abstract Fragment createFragment();

    /**
     * initial the Activity and register the fragment.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
}
