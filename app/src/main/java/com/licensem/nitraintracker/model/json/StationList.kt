package com.licensem.nitraintracker.model.json

data class StationList(val stations: List<Station> = emptyList()) {

    fun getStationByCode(stationCode: String): Station? {
        return stations.firstOrNull { (_, code) -> stationCode == code }
    }

    fun getStationByName(stationName: String): Station? {
        return stations.firstOrNull { (name, _) -> stationName == name }
    }

}