package com.licensem.nitraintracker.model.xml

import org.junit.Assert.assertEquals
import org.junit.Test

class ServiceTest {
    @Test
    fun testGetEstimatedTime() {
        val service = Service(
                serviceType = "Through",
                departTime = DepartTime("1029", "20170906102900"),
                delay = 4,
                destination = Destination("Great Victoria St", "1154", "1155"),
                platform = 2,
                callingPoints = emptyList()
        )
        assertEquals("10:33", service.getEstimatedTime())

        service.delay = 0
        assertEquals("10:29", service.getEstimatedTime())
    }

}