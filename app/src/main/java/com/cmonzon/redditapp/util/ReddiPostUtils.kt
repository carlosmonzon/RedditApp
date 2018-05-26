package com.cmonzon.redditapp.util

import com.cmonzon.redditapp.network.RedditService

import java.util.Locale

/**
 * @author cmonzon
 */

/**
 * Utils method which create a shortened number string from the given int
 * i.e: input 10900, result: 10.9k
 *
 * @param count integer to be converted
 * @return shortened number as string
 */
val Int.stringSuffix: String
    get () {
        if (this < 10000) return "" + this
        val exp = (Math.log(toDouble()) / Math.log(1000.0)).toInt()
        return String.format(Locale.getDefault(), "%.1f%c", this / Math.pow(1000.0, exp.toDouble()), "kMG"[exp - 1])
    }

fun String.buildPostUrl(): String {
    return String.format(Locale.getDefault(), "%1s%2s", RedditService.API_URL, this)
}
