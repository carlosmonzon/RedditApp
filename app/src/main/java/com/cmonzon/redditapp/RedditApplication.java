package com.cmonzon.redditapp;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author cmonzon
 */
public class RedditApplication extends Application {

    private static boolean initializedPicasso = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!initializedPicasso) {
            setUpPicasso();
            initializedPicasso = true;
        }
    }

    private void setUpPicasso() {
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 5 * 1024 * 1024);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().cache(cache);
        Picasso.Builder picassoBuilder = new Picasso.Builder(getApplicationContext());
        picassoBuilder.downloader(new OkHttp3Downloader(clientBuilder.build()));
        Picasso picasso = picassoBuilder.build();
        Picasso.setSingletonInstance(picasso);
    }
}
