package com.cmonzon.redditapp.data

import com.google.gson.annotations.SerializedName

/**
 * @author cmonzon
 */

class RedditPost(var data: RedditPostData? = null)

class RedditPostData {

    @SerializedName("subreddit")
    var subReddit: String? = null

    @SerializedName("ups")
    var votes: Int = 0

    var id: String? = null

    var author: String? = null

    var title: String? = null

    @SerializedName("permalink")
    var postUrlPath: String? = null

    var preview: Preview? = null
}

data class Preview(var images: List<PreviewImage>? = null, var isEnabled: Boolean = false)

data class PreviewImage(var resolutions: List<PreviewSource>? = null)

data class PreviewSource(var url: String? = null, var width: Int = 0, var height: Int = 0)

data class RedditData(@SerializedName("children") var posts: List<RedditPost>? = null, var dist: Int = 0)

class RedditFrontPage(var data: RedditData? = null)