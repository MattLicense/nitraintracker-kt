package com.licensem.nitraintracker.util

import org.simpleframework.xml.transform.Transform
import java.text.SimpleDateFormat
import java.util.*

class DateFormatTransformer : Transform<Date> {

    companion object {
        /**
         * Takes the time format hhmm and outputs hh:mm
         * @param time - time in hhmm format
         * @return hh:mm format of time provided, or empty string if empty
         */
        fun formatTime(time: String): String {
            return if (time == "") {
                ""
            } else {
                time.substring(0..1).plus(":").plus(time.substring(2..3))
            }
        }
    }

    private val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)

    override fun read(value: String?): Date {
        return df.parse(value)
    }

    override fun write(value: Date?): String {
        return df.format(value)
    }
}