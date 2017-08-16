package com.licensem.nitraintracker.util

import org.simpleframework.xml.transform.Transform
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateFormatTransformer : Transform<Date> {

    private var df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

    override fun read(value: String?): Date {
        return df.parse(value)
    }

    override fun write(value: Date?): String {
        return df.format(value)
    }
}