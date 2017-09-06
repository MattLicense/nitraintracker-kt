package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.*
import java.text.SimpleDateFormat
import java.util.*

@Root(name = "Service", strict = false)
data class Service(
        @get:Attribute(name = "Type") @get:Path("ServiceType") @set:Attribute(name = "Type") @set:Path("ServiceType") var serviceType: String? = null,
        @get:Element(name = "DepartTime") @set:Element(name = "DepartTime") var departTime: DepartTime? = null,
        @get:Attribute(name="Minutes") @get:Path("Delay") @set:Attribute(name="Minutes") @set:Path("Delay") var delay: Int = 0,
        @get:Element(name = "Destination1") @set:Element(name = "Destination1") var destination: Destination? = null,
        @get:Attribute(name = "Number") @get:Path("Platform") @set:Attribute(name = "Number") @set:Path("Platform") var platform: Int? = null,
        @get:ElementList(name = "Dest1CallingPoints", type = CallingPoint::class, required = false) @set:ElementList(name = "Dest1CallingPoints", type = CallingPoint::class, required = false) var callingPoints: List<CallingPoint> = ArrayList()
) {
    fun getEstimatedTime(): String? {
        val calendar = Calendar.getInstance()
        calendar.time = departTime!!.asDate()
        calendar.add(Calendar.MINUTE, delay)
        return SimpleDateFormat("HH:mm", Locale.ENGLISH).format(calendar.time)
    }
}