package com.licensem.nitraintracker.listeners

import android.view.View
import android.widget.*
import com.licensem.nitraintracker.FavouritesDatabase
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.FavouriteTrip
import com.licensem.nitraintracker.model.xml.Service
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class StationSelectorListener: AdapterView.OnItemSelectedListener, AnkoLogger {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        return
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val parentView = parent?.parent as View

        var resultsView = parentView.findViewById<ListView>(R.id.result_view)

        val origin = parentView.findViewById<Spinner>(R.id.originSelector).selectedItem.toString()
        val dest = parentView.findViewById<Spinner>(R.id.destinationSelector).selectedItem.toString()

        if(resultsView.tag != origin + "-" + dest) {
            info { "Clearing results after a station was changed" }
            resultsView.adapter = ArrayAdapter<Service>(parentView.context, android.R.layout.list_content, emptyArray())
        }

        // only enable the search button if origin / destination have been selected and they're different stations
        var searchButton = parentView.findViewById<Button>(R.id.search_button)
        if(origin != "" && dest != "" && origin != dest) {
            searchButton.isEnabled = true
            searchButton.visibility = View.VISIBLE
        } else {
            searchButton.isEnabled = false
            searchButton.visibility = View.INVISIBLE
        }

        // make sure the favourites button is highlighted if this is a favourite
        parentView.findViewById<ToggleButton>(R.id.favourite_button).isChecked = FavouritesDatabase.exists(parent?.context, FavouriteTrip(origin,dest))
    }

}
