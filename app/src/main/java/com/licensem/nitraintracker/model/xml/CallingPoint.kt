package com.licensem.nitraintracker.model.xml

import com.licensem.nitraintracker.util.DateFormatTransformer
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(strict = false)
data class CallingPoint(
    @get:Attribute(name="Name") @set:Attribute(name="Name") var name: String = "",
    @get:Attribute(name="ttdep") @set:Attribute(name="ttdep") var scheduledDeparture: String = "",
    @get:Attribute(name="etdep") @set:Attribute(name="etdep") var estimatedDeparture: String = ""
) {
    companion object {
        fun createFromDestination(destination: Destination) : CallingPoint {
            return CallingPoint(destination.name, destination.scheduledArrival, destination.estimatedArrival)
        }
    }

    fun getScheduledTime() : String {
        return DateFormatTransformer.formatTime(scheduledDeparture)
    }

    fun getEstimatedTime() : String {
        return DateFormatTransformer.formatTime(estimatedDeparture)
    }
}