package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name="Destination1", strict=false)
data class Destination(
    @get:Attribute @set:Attribute var name: String? = null,
    @get:Attribute(name="ttarr") @set:Attribute(name="ttarr") var scheduledArrival: String? = null,
    @get:Attribute(name="etarr") @set:Attribute(name="etarr") var estimatedArrival: String? = null
) {
    fun getScheduledTime() : String {
        return scheduledArrival?.substring(0..1).plus(":").plus(scheduledArrival?.substring(2..3))
    }

    fun getEstimatedTime() : String {
        return estimatedArrival?.substring(0..1).plus(":").plus(estimatedArrival?.substring(2..3))
    }
}