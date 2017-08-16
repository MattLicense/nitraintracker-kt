package com.licensem.nitraintracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.licensem.nitraintracker.model.json.StationList
import com.licensem.nitraintracker.tasks.StationListTask
import com.licensem.nitraintracker.view.FavouriteToggleListener
import com.licensem.nitraintracker.view.SearchClickListener
import com.licensem.nitraintracker.view.StationSelectorListener

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.toolbar)

        val origin = intent.getStringExtra("origin")
        val destination = intent.getStringExtra("destination")

        var stationList: StationList = StationListTask().execute().get()!!
        var stationNames: List<String> = stationList.stations!!.map { (name) -> name }

        var favouriteButton = findViewById(R.id.favourite_button) as ToggleButton
        favouriteButton.setOnCheckedChangeListener(FavouriteToggleListener())

        var originSpinner = findViewById(R.id.originSelector)!! as Spinner
        var destinationSpinner = findViewById(R.id.destinationSelector)!! as Spinner
        setupSpinner(originSpinner, stationNames)
        setupSpinner(destinationSpinner, stationNames)

        var searchButton = findViewById(R.id.search_button)!! as Button
        searchButton.setOnClickListener(SearchClickListener())

        var swapButton = findViewById(R.id.swap_button)!! as ImageView
        swapButton.setOnClickListener {
            var origin = originSpinner.selectedItem.toString()
            var destination = destinationSpinner.selectedItem.toString()

            destinationSpinner.setSelection(getIndex(destinationSpinner, origin))
            originSpinner.setSelection(getIndex(originSpinner, destination))
        }

        if(origin != null && destination != null && origin != "" && destination != "") {
            System.setProperty("clearResults", "false")
            originSpinner.setSelection(getIndex(originSpinner, origin))
            destinationSpinner.setSelection(getIndex(destinationSpinner, destination))

            searchButton.callOnClick()
        }
    }

    private fun getIndex(haystack: Spinner, needle: String): Int {
        return (0 until haystack.count).firstOrNull { haystack.getItemAtPosition(it) == needle } ?: 0
    }

    private fun setupSpinner(spinner: Spinner, stationNames: List<String>) {
        spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, stationNames)
        spinner.onItemSelectedListener = StationSelectorListener()
    }

}
