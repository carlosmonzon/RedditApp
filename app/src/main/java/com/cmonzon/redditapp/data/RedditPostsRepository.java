package com.cmonzon.redditapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Single;

/**
 * @author cmonzon
 */
public class RedditPostsRepository implements RedditPostsDataSource {

    private static volatile RedditPostsRepository INSTANCE;

    @NonNull
    private RedditPostsDataSource remoteDataSource;

    @Nullable
    private RedditFrontPage cachedRedditFrontPage;

    //mark cache as invalid
    boolean cacheIsDirty = false;

    public RedditPostsRepository(@NonNull RedditPostsDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static RedditPostsRepository getInstance(@NonNull RedditPostsDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new RedditPostsRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Single<RedditFrontPage> getRedditFrontPage() {
        //respond with cache data immediately if it isn't dirty
        if (cachedRedditFrontPage != null && !cacheIsDirty) {
            return Single.just(cachedRedditFrontPage);
        } else {
            //request RedditFrontPage from remote data source
            return remoteDataSource.getRedditFrontPage().doOnSuccess(frontPage -> {
                //save front page to cache
                cachedRedditFrontPage = frontPage;
                cacheIsDirty = false;
            });
        }
    }

    @Override
    public void refreshFrontPage() {
        cacheIsDirty = true;
    }
}
