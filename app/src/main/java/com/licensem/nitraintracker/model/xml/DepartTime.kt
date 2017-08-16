package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(strict = false)
data class DepartTime(
    @get:Attribute(name="time") @set:Attribute(name="time") var time: String? = null,
    @get:Attribute(name="sorttimestamp") @set:Attribute(name="sorttimestamp") var timestamp: String? = null
) {
    fun getFormattedTime() : String {
        return time?.substring(0..1).plus(":").plus(time?.substring(2..3))
    }
}