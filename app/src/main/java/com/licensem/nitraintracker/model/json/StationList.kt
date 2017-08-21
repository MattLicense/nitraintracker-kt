package com.licensem.nitraintracker.model.json

data class StationList(val stations: List<Station> = emptyList()) {

    fun getStationByCode(stationCode: String): Station? {
        return stations.firstOrNull { (code) -> code == stationCode }
    }

    fun getStationByName(stationName: String): Station? {
        return stations.firstOrNull { (name) -> name == stationName }
    }

}