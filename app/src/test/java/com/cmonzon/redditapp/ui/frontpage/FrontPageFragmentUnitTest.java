package com.cmonzon.redditapp.ui.frontpage;


import com.cmonzon.redditapp.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

/**
 * @author cmonzon
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FrontPageFragmentUnitTest {

    @Mock
    FrontPageContract.Presenter presenter;

    FrontPageFragment frontPageFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        frontPageFragment = FrontPageFragment.newInstance();
        frontPageFragment.setPresenter(presenter);
    }

    @Test
    public void assertFragmentIsLoaded() {
        SupportFragmentTestUtil.startFragment(frontPageFragment);
        assertNotNull(frontPageFragment.getView());
        verify(presenter).start();
    }

    @Test
    public void test_fragment_stop() {
        SupportFragmentTestUtil.startFragment(frontPageFragment);
        frontPageFragment.onPause();
        verify(presenter).unSubscribe();
    }
}
