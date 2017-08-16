package com.licensem.nitraintracker.model.json

data class StationList(val stations: List<Station> = emptyList()) {

    fun getStationByCode(code: String): Station? {
        return stations.filter({ s -> s.code.equals(code)}).firstOrNull()
    }

    fun getStationByName(name: String): Station? {
        return stations.filter({ s -> s.name.equals(name)}).firstOrNull()
    }

}