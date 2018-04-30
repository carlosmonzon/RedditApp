package com.cmonzon.redditapp;

import android.support.test.espresso.IdlingRegistry;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import okhttp3.OkHttpClient;

/**
 * @author cmonzon
 */
public class IdlingResources {
    public static void registerOkHttp(OkHttpClient client) {
        //register OkHttp Idling resource
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create(
                "okhttp", client));
    }
}
