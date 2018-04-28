package com.cmonzon.redditapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author cmonzon
 */
public class RedditData {

    @SerializedName("children")
    private List<RedditPost> posts;

    private int dist;

    public List<RedditPost> getPosts() {
        return posts;
    }

    public void setPosts(List<RedditPost> posts) {
        this.posts = posts;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
}
