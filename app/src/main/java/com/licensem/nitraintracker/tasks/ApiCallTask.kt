package com.licensem.nitraintracker.tasks

import android.os.AsyncTask
import com.licensem.nitraintracker.ApiConfig
import com.licensem.nitraintracker.util.DateFormatTransformer
import com.licensem.nitraintracker.model.xml.StationBoard
import org.simpleframework.xml.core.Persister
import java.net.HttpURLConnection
import java.net.URL
import org.simpleframework.xml.transform.RegistryMatcher
import java.util.*


class ApiCallTask : AsyncTask<String, Void?, StationBoard?>() {

    override fun doInBackground(vararg stationCode: String): StationBoard? {
        val serializer = Persister(RegistryMatcher().apply { this.bind(Date::class.java, DateFormatTransformer()) })
        val connection = URL(ApiConfig.API_URL.plus(stationCode[0]).plus(".xml")).openConnection() as HttpURLConnection

        return serializer.read(StationBoard::class.java, connection.inputStream)
    }

}