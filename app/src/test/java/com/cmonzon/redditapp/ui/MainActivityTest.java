package com.cmonzon.redditapp.ui;

import android.support.v4.app.Fragment;

import com.cmonzon.redditapp.BuildConfig;
import com.cmonzon.redditapp.ui.frontpage.FrontPageFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author cmonzon
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private ActivityController<MainActivity> controller;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(MainActivity.class);
    }

    @After
    public void destroy() {
        controller.destroy();
    }

    @Test
    public void assertFragmentIsLoaded() {
        MainActivity activity = controller.create().start().resume().visible().get();
        assertNotNull(activity);
        //get list of fragments in support manager
        List<Fragment> fragmentList = activity.getSupportFragmentManager().getFragments();
        //only one fragment must be on the stack
        assertEquals(fragmentList.size(), 1);
        Fragment currentFragment = fragmentList.get(0);
        //test the current fragment is instance of FrontPageFragment
        assertTrue(currentFragment instanceof FrontPageFragment);
    }

    @Test
    public void assertOnConfigurationChanged() {
        controller.configurationChange();
        MainActivity activity = controller.create().start().resume().visible().get();
        //get list of fragments in support manager
        List<Fragment> fragmentList = activity.getSupportFragmentManager().getFragments();
        //only one fragment must be on the stack
        assertEquals(fragmentList.size(), 1);
    }
}
