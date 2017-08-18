package com.licensem.nitraintracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.licensem.nitraintracker.model.json.StationList
import com.licensem.nitraintracker.tasks.StationListTask
import com.licensem.nitraintracker.views.SearchView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.setContentView

class SearchActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var stationList: StationList = StationListTask().execute().get()!!
        var stationNames: List<String> = stationList.stations!!.map { (name) -> name }

        SearchView().stations(stationNames).setContentView(this)
        info("Entered search view")

        val origin = intent.getStringExtra("origin")
        val destination = intent.getStringExtra("destination")

        var originSpinner = findViewById(com.licensem.nitraintracker.R.id.originSelector)!! as Spinner
        var destinationSpinner = findViewById(com.licensem.nitraintracker.R.id.destinationSelector)!! as Spinner

        if(origin != null && destination != null && origin != "" && destination != "") {
            originSpinner.setSelection(getIndex(originSpinner, origin))
            destinationSpinner.setSelection(getIndex(destinationSpinner, destination))

            var searchButton = findViewById(R.id.search_button)!! as Button
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
    private fun getIndex(haystack: Spinner, needle: String): Int {
        return (0 until haystack.count).firstOrNull { haystack.getItemAtPosition(it) == needle } ?: 0
    }

}
