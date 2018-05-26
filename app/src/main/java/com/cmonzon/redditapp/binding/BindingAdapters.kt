package com.cmonzon.redditapp.binding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.cmonzon.redditapp.R
import com.cmonzon.redditapp.data.RedditPostData
import com.cmonzon.redditapp.util.stringSuffix
import com.squareup.picasso.Picasso


/**
 * @author cmonzon
 */

@BindingAdapter("redditPostImage")
fun ImageView.redditPostImage(post: RedditPostData) {
    if (post.preview != null && post.preview!!.images != null) {
        val previewImageList = post.preview!!.images
        if (!previewImageList!!.isEmpty()) {
            val sourceList = previewImageList.iterator().next().resolutions
            if (!sourceList!!.isEmpty()) {
                val (url) = sourceList.iterator().next()
                visibility = View.VISIBLE
                Picasso.with(context).load(url).error(R.mipmap.ic_launcher).into(this)
            } else {
                visibility = View.GONE
            }
        } else {
            visibility = View.GONE
        }
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("redditVotes")
fun TextView.redditVotes(votes: Int) {
    text = votes.stringSuffix
}

