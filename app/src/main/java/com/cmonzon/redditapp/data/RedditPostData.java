package com.cmonzon.redditapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author cmonzon
 */
public class RedditPostData {

    @SerializedName("subreddit_id")
    private String subRedditId;

    @SerializedName("subreddit")
    private String subReddit;

    @SerializedName("subreddit_name_prefixed")
    private String subRedditPrefixed;

    @SerializedName("ups")
    private int votes;

    private String id;

    private String author;

    private String title;

    private String url;

    private Preview preview;

    public String getSubRedditId() {
        return subRedditId;
    }

    public void setSubRedditId(String subRedditId) {
        this.subRedditId = subRedditId;
    }

    public String getSubReddit() {
        return subReddit;
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }

    public String getSubRedditPrefixed() {
        return subRedditPrefixed;
    }

    public void setSubRedditPrefixed(String subRedditPrefixed) {
        this.subRedditPrefixed = subRedditPrefixed;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }
}
