package com.licensem.nitraintracker.model.json

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StationListTest {

    private val STATIONS = mapOf("Adelaide" to "3042A0", "Antrim" to "3042A1",
            "Ballycarry" to "3042A3", "Ballymena" to "3042A2", "Ballymoney" to "3042AA", "Balmoral" to "3042A5")
    private var stationList: StationList? = null

    @Before
    fun setUp() {
        val stations: MutableList<Station> = mutableListOf()
        STATIONS.forEach { name, code -> stations.add(Station(name, code)) }
        stationList = StationList(stations)
    }

    @Test
    fun testGetStationByCode() {
        assertNotNull(stationList)
        STATIONS.forEach { name, code ->
            val expected = Station(name, code)
            val actual = stationList!!.getStationByCode(code)
            assertEquals(expected, actual)
        }
        assertNull(stationList!!.getStationByCode("code123"))
    }

    @Test
    fun testGetStationByName() {
        assertNotNull(stationList)
        STATIONS.forEach { name, code ->
            val expected = Station(name, code)
            val actual = stationList!!.getStationByName(name)
            assertEquals(expected, actual)
        }
        assertNull(stationList!!.getStationByName("station123"))
    }

}