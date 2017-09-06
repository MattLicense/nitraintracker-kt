package com.licensem.nitraintracker.model.xml

import com.licensem.nitraintracker.util.DateFormatTransformer
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name="Destination1", strict=false)
data class Destination(
    @get:Attribute @set:Attribute var name: String = "",
    @get:Attribute(name="ttarr") @set:Attribute(name="ttarr") var scheduledArrival: String = "",
    @get:Attribute(name="etarr") @set:Attribute(name="etarr") var estimatedArrival: String = ""
) {
    fun getScheduledTime() : String {
        return DateFormatTransformer.formatTime(scheduledArrival)
    }

    fun getEstimatedTime() : String {
        return DateFormatTransformer.formatTime(estimatedArrival)
    }
}