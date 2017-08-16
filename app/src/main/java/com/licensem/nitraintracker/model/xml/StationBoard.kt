package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

@Root(strict = false)
data class StationBoard(
    @get:Attribute(name = "name") @set:Attribute(name = "name") var name: String? = null,
    @get:Attribute(name = "Timestamp") @set:Attribute(name = "Timestamp") var updateDate: Date? = null,
    @get:ElementList(inline = true, type = Service::class, required = false, entry = "Service") @set:ElementList(inline = true, type = Service::class, required = false, entry = "Service") var services: List<Service> = ArrayList()
) {
    fun findTrainsTo(destination: String) : List<Service> {
        return services.filterNot { s -> s.serviceType.equals("Terminating") }
                .sortedBy { s -> s.departTime!!.timestamp }
                .filter {
                    s ->  s.callingPoints.filter { c -> c.name.equals(destination) }.isNotEmpty()
                            || s.destination!!.name!!.equals(destination)
                }
    }
}