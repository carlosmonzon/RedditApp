package com.cmonzon.redditapp.util;

import com.cmonzon.redditapp.network.RedditService;

import java.util.Locale;

/**
 * @author cmonzon
 */
public final class ReddiPostUtils {

    private static final String SUFFIX_GROUP = "kMG";

    /**
     * Utils method which create a shortened number string from the given int
     * i.e: input 10900, result: 10.9k
     *
     * @param count integer to be converted
     * @return shortened number as string
     */
    public static String withSuffix(int count) {
        if (count < 10000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format(Locale.getDefault(), "%.1f%c", count / Math.pow(1000, exp), SUFFIX_GROUP.charAt(exp - 1));
    }

    public static String buildPostUrl(String path) {
        return String.format(Locale.getDefault(), "%1s%2s", RedditService.API_URL, path);
    }

}
