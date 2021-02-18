package com.waxym

import com.waxym.utils.buildFizzBuzzList
import org.junit.Assert
import org.junit.Test


class FizzBuzzListTest {

    @Test
    fun `should have 0 element`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 0)
        Assert.assertEquals(0, result.size)
    }

    @Test
    fun `should have 1 element`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 1)
        Assert.assertEquals(1, result.size)
    }

    @Test
    fun `should have 10 elements`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10)
        Assert.assertEquals(10, result.size)
    }

    @Test
    fun `should start at 10 and finish at 15`() {
        val result: List<String> = buildFizzBuzzList(start = 10, end = 15)
        Assert.assertEquals(6, result.size)
        Assert.assertEquals("10", result[0])
        Assert.assertEquals("15", result[result.lastIndex])
    }

    @Test
    fun `fizz multiple should be able to be 0`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, fizzMultiple = 0, fizzLabel = "fizz")
        Assert.assertEquals(10, result.size)
        Assert.assertTrue(result.none { it == "fizz" })
    }

    @Test
    fun `buzz multiple should be able to be 0`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, buzzMultiple = 0, buzzLabel = "buzz")
        Assert.assertEquals(10, result.size)
        Assert.assertTrue(result.none { it == "buzz" })
    }

    @Test
    fun `fizz-buzz should not divide by 0`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, fizzMultiple = 0, buzzMultiple = 0)
        Assert.assertEquals(10, result.size)
    }

    @Test
    fun `should be a list of int`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10)
        Assert.assertEquals(10, result.size)
        Assert.assertEquals(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), result)
    }

    @Test
    fun `should be a list with fizz multiple of 3`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, fizzMultiple = 3, fizzLabel = "fizz")
        Assert.assertEquals(10, result.size)
        Assert.assertEquals(listOf("1", "2", "fizz", "4", "5", "fizz", "7", "8", "fizz", "10"), result)
    }

    @Test
    fun `should be a list with buzz multiple of 4`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, buzzMultiple = 4, buzzLabel = "buzz")
        Assert.assertEquals(10, result.size)
        Assert.assertEquals(listOf("1", "2", "3", "buzz", "5", "6", "7", "buzz", "9", "10"), result)
    }

    @Test
    fun `should be a list with fizz-buzz multiple of 1 (ignoring fizz and buzz)`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, fizzMultiple = 1, fizzLabel = "fizz", buzzMultiple = 1, buzzLabel = "buzz")
        Assert.assertEquals(10, result.size)
        Assert.assertEquals(listOf("fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz", "fizzbuzz"), result)
    }

    @Test
    fun `should be a list alternating between fizz and fizzbuzz (ignoring buzz)`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 10, fizzMultiple = 1, fizzLabel = "fizz", buzzMultiple = 2, buzzLabel = "buzz")
        Assert.assertEquals(10, result.size)
        Assert.assertEquals(listOf("fizz", "fizzbuzz", "fizz", "fizzbuzz", "fizz", "fizzbuzz", "fizz", "fizzbuzz", "fizz", "fizzbuzz"), result)
    }

    @Test
    fun `spec should work`() {
        val result: List<String> = buildFizzBuzzList(start = 1, end = 100, fizzMultiple = 3, fizzLabel = "fizz", buzzMultiple = 5, buzzLabel = "buzz")
        Assert.assertEquals(100, result.size)

        result.forEachIndexed { index, value ->
            when {
                (index + 1) % 15 == 0 -> Assert.assertEquals("fizzbuzz", value)
                (index + 1) % 5 == 0 -> Assert.assertEquals("buzz", value)
                (index + 1) % 3 == 0 -> Assert.assertEquals("fizz", value)
                else -> Assert.assertEquals("${index + 1}", value)
            }
        }
    }
}