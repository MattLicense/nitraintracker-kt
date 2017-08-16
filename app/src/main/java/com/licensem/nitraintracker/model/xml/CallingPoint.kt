package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(strict = false)
data class CallingPoint(
    @get:Attribute(name="Name") @set:Attribute(name="Name") var name: String = "",
    @get:Attribute(name="ttdep") @set:Attribute(name="ttdep") var scheduledDeparture: String = "",
    @get:Attribute(name="etdep") @set:Attribute(name="etdep") var estimatedDeparture: String = ""
) {
    fun getScheduledTime() : String {
        return scheduledDeparture?.substring(0..1).plus(":").plus(scheduledDeparture?.substring(2..3))
    }

    fun getEstimatedTime() : String {
        return estimatedDeparture?.substring(0..1).plus(":").plus(estimatedDeparture?.substring(2..3))
    }
}