package com.cmonzon.redditapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author cmonzon
 */
public class RedditPostData {


    @SerializedName("subreddit")
    private String subReddit;


    @SerializedName("ups")
    private int votes;

    private String id;

    private String author;

    private String title;

    @SerializedName("permalink")
    private String postUrlPath;

    private Preview preview;

    public String getSubReddit() {
        return subReddit;
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getPostUrlPath() {
        return postUrlPath;
    }

    public void setPostUrlPath(String postUrlPath) {
        this.postUrlPath = postUrlPath;
    }
}
