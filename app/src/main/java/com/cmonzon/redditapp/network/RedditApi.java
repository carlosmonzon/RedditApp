package com.cmonzon.redditapp.network;

import com.cmonzon.redditapp.data.RedditFrontPage;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * @author cmonzon
 */
public interface RedditApi {

    @GET(".json")
    Single<RedditFrontPage> getRedditFrontPage();

}
