package com.cmonzon.redditapp.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.cmonzon.redditapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasHost;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author cmonzon
 */
public class FrontPageScreenTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);


    @Test
    public void showRedditPost_errorMessageIsHide() {
        //error message shouldn't be displayed
        onView(withId(R.id.tv_error_message_display)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rv_posts)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRedditPost_validateIntentExtra() {
        blockExternalIntents();
        //scroll to top
        onView(withId(R.id.rv_posts)).perform(swipeDown());
        //click on first item on the list
        onView(withId(R.id.rv_posts)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //verify intend has data related to www.reddit.com according to API_URL
        intended(allOf(
                hasAction(equalTo(Intent.ACTION_VIEW)),
                hasData(hasHost(equalTo("www.reddit.com")))));
    }

    private static void blockExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }
}
