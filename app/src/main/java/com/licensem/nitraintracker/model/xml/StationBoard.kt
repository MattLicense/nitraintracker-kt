package com.licensem.nitraintracker.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.Date
import kotlin.collections.ArrayList

@Root(strict = false)
data class StationBoard(
    @get:Attribute(name = "name") @set:Attribute(name = "name") var name: String? = null,
    @get:Attribute(name = "Timestamp") @set:Attribute(name = "Timestamp") var updateDate: Date? = null,
    @get:ElementList(inline = true, type = Service::class, required = false, entry = "Service") @set:ElementList(inline = true, type = Service::class, required = false, entry = "Service") var services: List<Service> = ArrayList()
) {

    val NOT_TERMINATING = { (serviceType) : Service -> serviceType != "Terminating" }
    val CALLS_AT_STATION = { service: Service, destination: String ->
        service.destination?.name == destination || service.callingPoints.any { (stationName) -> stationName == destination }
    }

    fun findTrainsTo(destination: String) : List<Service> {
        return services.filter(NOT_TERMINATING)
                       .sortedBy(Service::getEstimatedTime)
                       .filter { CALLS_AT_STATION(it, destination) }
    }
}