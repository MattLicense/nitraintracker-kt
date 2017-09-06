package com.licensem.nitraintracker.model.xml

import com.licensem.nitraintracker.util.DateFormatTransformer
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.text.SimpleDateFormat
import java.util.*

@Root(strict = false)
data class DepartTime(
    @get:Attribute(name="time") @set:Attribute(name="time") var time: String = "",
    @get:Attribute(name="sorttimestamp") @set:Attribute(name="sorttimestamp") var timestamp: String = ""
) {
    fun getFormattedTime() : String {
        return DateFormatTransformer.formatTime(time)
    }

    fun asDate() : Date {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).parse(timestamp)
    }
}