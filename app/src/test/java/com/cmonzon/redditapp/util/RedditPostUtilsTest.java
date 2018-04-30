package com.cmonzon.redditapp.util;

import com.cmonzon.redditapp.network.RedditService;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author cmonzon
 */
public class RedditPostUtilsTest {

    @Test
    public void testWithSuffix_lower_than_break_point() {
        //test if withSuffix method does not convert the number if is lower than break point
        int breakPoint = 9999;
        String result = ReddiPostUtils.withSuffix(breakPoint);
        assertEquals(String.valueOf(breakPoint), result);
    }

    @Test
    public void testWithSuffix_higher_than_break_point() {
        //test if withSuffix method converts the number to expected output

        //Thousands
        int input1 = 10780;
        String expected1 = "10.8k";
        String result = ReddiPostUtils.withSuffix(input1);
        assertEquals(expected1, result);

        int input2 = 203333;
        String expected2 = "203.3k";
        String result2 = ReddiPostUtils.withSuffix(input2);
        assertEquals(expected2, result2);

        //Millions
        int input3 = 888888888;
        String expected3 = "888.9M";
        String result3 = ReddiPostUtils.withSuffix(input3);
        assertEquals(expected3, result3);

    }

    @Test
    public void testBuildPostUrl() {
        //test if buildPostUrl joins base url and path given
        String path = "/r/myPath";
        String expected = RedditService.API_URL + path;
        String result = ReddiPostUtils.buildPostUrl(path);
        assertEquals(expected, result);

    }
}
