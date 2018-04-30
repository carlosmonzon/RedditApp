package com.cmonzon.redditapp;

import okhttp3.OkHttpClient;

/**
 * @author cmonzon
 */
public class IdlingResources {
    public static void registerOkHttp(OkHttpClient client) {
        //on realease build there is not need to register IdlingResources
    }
}
