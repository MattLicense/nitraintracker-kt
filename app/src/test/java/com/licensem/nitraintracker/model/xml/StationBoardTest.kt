package com.licensem.nitraintracker.model.xml

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher

class StationBoardTest {

    var instance = StationBoard()

    @Before
    fun setUp() {
        val serializer = Persister(RegistryMatcher().apply {
            this.bind(java.util.Date::class.java, com.licensem.nitraintracker.util.DateFormatTransformer())
        })
        val connection = StationBoardTest::class.java.getResource("/stationBoardTest.xml")

        instance = serializer.read(StationBoard::class.java, connection.openStream())
    }

    @Test
    fun testNotTerminatingFilter() {
        assertNotEquals(0, instance.services.size)
        val notTerminatingFilter = (StationBoard::NOT_TERMINATING)(instance)
        assertFalse(notTerminatingFilter(instance.services[0]))

        val filtered = instance.services.filter(notTerminatingFilter)
        assertEquals(3, filtered.size)
        for(service: Service in filtered) {
            assertNotEquals("Terminating", service.serviceType)
        }
    }

    @Test
    fun testCallsAtStationFilter() {
        assertNotEquals(0, instance.services.size)
        val callsAtFilter = (StationBoard::CALLS_AT_STATION)(instance)
        assertTrue(callsAtFilter(instance.services[1], "Botanic"))
        assertFalse(callsAtFilter(instance.services[1], "Parkgate"))

        val filtered = instance.services.filter { s -> callsAtFilter(s, "Lurgan") }
        assertEquals(2, filtered.size)
        for(service: Service in filtered) {
            assertTrue(service.callingPoints.any { c -> c.name == "Lurgan" })
        }
    }

    @Test
    fun findTrainsTo() {
        assertNotEquals(0, instance.services.size)
        val results = instance.findTrainsTo("Holywood")
        assertEquals(3, results.size)

        results[0].apply {
            assertEquals("20170906095700", departTime?.timestamp)
            assertEquals(3, platform)
            assertEquals("Portadown", destination?.name)
            assertEquals(23, callingPoints.size)
        }

        results[1].apply {
            assertEquals("20170906102700", departTime?.timestamp)
            assertEquals(2, platform)
            assertEquals("Portadown", destination?.name)
            assertEquals(23, callingPoints.size)
        }

        results[2].apply {
            assertEquals("20170906105700", departTime?.timestamp)
            assertEquals(3, platform)
            assertEquals("Portadown", destination?.name)
            assertEquals(21, callingPoints.size)
        }
    }

}