package com.licensem.nitraintracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import com.licensem.nitraintracker.model.json.StationList
import com.licensem.nitraintracker.tasks.StationListTask
import com.licensem.nitraintracker.views.SearchView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.setContentView

class SearchActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stationList: StationList = StationListTask().execute().get()!!
        SearchView().setStationNames(stationList).setContentView(this)
        info("Entered search view")

        val origin = intent.getStringExtra("origin")
        val destination = intent.getStringExtra("destination")

        val originSpinner = findViewById(com.licensem.nitraintracker.R.id.originSelector)!! as Spinner
        val destinationSpinner = findViewById(com.licensem.nitraintracker.R.id.destinationSelector)!! as Spinner

        if(origin != null && destination != null && origin != "" && destination != "") {
            originSpinner.setSelection(getIndex(originSpinner, origin))
            destinationSpinner.setSelection(getIndex(destinationSpinner, destination))

            val searchButton = findViewById(R.id.search_button)!! as Button
            searchButton.callOnClick()
        }
    }

    /**
     * Returns the index of a string in the given spinner
     * @param haystack
     *          Spinner
     * @param needle
     *          text to find within the spinner
     * @return Int
     *          index of the text within the spinner
     */
    private fun getIndex(haystack: Spinner, needle: String) =
            (0 until haystack.count).firstOrNull { haystack.getItemAtPosition(it) == needle } ?: 0

}
