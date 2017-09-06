package com.licensem.nitraintracker.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateFormatTransformerTest {

    private val instance = DateFormatTransformer()
    private val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse("2017-09-05 15:00:00")

    @Test
    fun testRead() {
        val instance = DateFormatTransformer()
        val dateString = "05/09/2017 15:00:00"
        assertEquals(date, instance.read(dateString))
    }

    @Test
    fun testWrite() {
        assertEquals("05/09/2017 15:00:00", instance.write(date))
    }

    @Test
    fun testFormatTime() {
        val input = "1500"
        assertEquals("15:00", DateFormatTransformer.formatTime(input))
        assertEquals("", DateFormatTransformer.formatTime(""))
    }

}