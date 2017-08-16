package com.licensem.nitraintracker.tasks

import android.os.AsyncTask
import com.licensem.nitraintracker.ApiConfig
import com.licensem.nitraintracker.model.json.StationList
import java.net.URL
import com.fasterxml.jackson.databind.ObjectMapper

class StationListTask : AsyncTask<Void, Void?, StationList?>() {

    override fun doInBackground(vararg p0: Void): StationList? {
        return ObjectMapper().readValue(URL(ApiConfig.API_URL), StationList::class.java)
    }


}