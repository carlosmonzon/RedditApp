package com.cmonzon.redditapp.util

import com.cmonzon.redditapp.network.RedditService

import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * @author cmonzon
 */
class RedditPostUtilsTest {

    @Test
    fun testWithSuffix_lower_than_break_point() {
        //test if withSuffix method does not convert the number if is lower than break point
        val breakPoint = 9999
        val result = breakPoint.stringSuffix
        assertEquals(breakPoint.toString(), result)
    }

    @Test
    fun testWithSuffix_higher_than_break_point() {
        //test if withSuffix method converts the number to expected output

        //Thousands
        val input1 = 10780
        val expected1 = "10.8k"
        val result = input1.stringSuffix
        assertEquals(expected1, result)

        val input2 = 203333
        val expected2 = "203.3k"
        val result2 = input2.stringSuffix
        assertEquals(expected2, result2)

        //Millions
        val input3 = 888888888
        val expected3 = "888.9M"
        val result3 = input3.stringSuffix
        assertEquals(expected3, result3)

    }

    @Test
    fun testBuildPostUrl() {
        //test if buildPostUrl joins base url and path given
        val path = "/r/myPath"
        val expected = RedditService.API_URL + path
        val result = path.buildPostUrl()
        assertEquals(expected, result)
    }
}
