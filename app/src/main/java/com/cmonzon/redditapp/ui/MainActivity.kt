package com.cmonzon.redditapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.cmonzon.redditapp.R
import com.cmonzon.redditapp.ui.frontpage.FrontPageFragment
import com.cmonzon.redditapp.util.ActivityUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.findFragmentById(R.id.container) as? FrontPageFragment

        if (fragment == null) {
            // Create the fragment
            fragment = FrontPageFragment.newInstance()
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.container)
        }
    }
}
