package com.cmonzon.redditapp.data;

import io.reactivex.Single;

/**
 * @author cmonzon
 */
public interface RedditPostsDataSource {

    Single<RedditFrontPage> getRedditFrontPage();

    void refreshFrontPage();

}
