package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.*
import java.text.SimpleDateFormat
import java.util.*

@Root(name = "Service", strict = false)
data class Service(
        @get:Path("ServiceType/@Type") @set:Path("ServiceType/@Type") var serviceType: String? = null,
        @get:Element(name = "DepartTime") @set:Element(name = "DepartTime") var departTime: DepartTime? = null,
        @get:Element(name = "Delay") @set:Element(name = "Delay") var delay: Delay? = null,
        @get:Element(name = "Origin1") @set:Element(name = "Origin1") var origin: Origin? = null,
        @get:Element(name = "Destination1") @set:Element(name = "Destination1") var destination: Destination? = null,
        @get:Element(name = "Platform") @set:Element(name = "Platform") var platform: Platform? = null,
        @get:ElementList(name = "Dest1CallingPoints", type = CallingPoint::class, required = false) @set:ElementList(name = "Dest1CallingPoints", type = CallingPoint::class, required = false) var callingPoints: List<CallingPoint> = ArrayList()
) {
    fun getEstimatedTime(): String? {
        var calendar = Calendar.getInstance()
        calendar.time = departTime!!.asDate()
        calendar.add(Calendar.MINUTE, Integer.parseInt(delay!!.delayMinutes?: 0.toString()))
        return SimpleDateFormat("HHmm").format(calendar.time)
    }
}