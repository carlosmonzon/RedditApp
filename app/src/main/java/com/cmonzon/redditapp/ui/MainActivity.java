package com.cmonzon.redditapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmonzon.redditapp.R;
import com.cmonzon.redditapp.ui.frontpage.FrontPageFragment;
import com.cmonzon.redditapp.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrontPageFragment fragment = (FrontPageFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            // Create the fragment
            fragment = FrontPageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.container);
        }
    }
}
