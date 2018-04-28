package com.cmonzon.redditapp.data.remote;

import com.cmonzon.redditapp.data.RedditFrontPage;
import com.cmonzon.redditapp.data.RedditPostsDataSource;
import com.cmonzon.redditapp.network.RedditApi;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * @author cmonzon
 */
public class RedditPostsRemoteDataSource implements RedditPostsDataSource {

    private static volatile RedditPostsRemoteDataSource INSTANCE;

    private RedditApi api;

    private Scheduler ioScheduler;

    public RedditPostsRemoteDataSource(RedditApi api, Scheduler ioScheduler) {
        this.api = api;
        this.ioScheduler = ioScheduler;
    }

    public static RedditPostsRemoteDataSource getInstance(RedditApi api, Scheduler scheduler) {
        if (INSTANCE == null) {
            INSTANCE = new RedditPostsRemoteDataSource(api, scheduler);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Single<RedditFrontPage> getRedditFrontPage() {
        return api.getRedditFrontPage().subscribeOn(ioScheduler);
    }

    @Override
    public void refreshFrontPage() {
        //not implemented
    }
}
