package com.licensem.nitraintracker.view

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.Toast
import com.licensem.nitraintracker.FavouritesDatabase
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.FavouriteTrip

class FavouriteToggleListener : CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var originSpinner = (buttonView?.parent?.parent as View).findViewById<Spinner>(R.id.originSelector)
        var destinationSpinner = (buttonView?.parent?.parent as View).findViewById<Spinner>(R.id.destinationSelector)

        val favourite = FavouriteTrip(originSpinner.selectedItem.toString(), destinationSpinner.selectedItem.toString())

        val favouriteExists = FavouritesDatabase.exists(buttonView?.context, favourite)

        if(isChecked && !favouriteExists) {
            addToFavourites(favourite, buttonView?.context)
        } else if(!isChecked && favouriteExists) {
            removeFromFavourites(favourite, buttonView?.context)
        }
    }

    private fun addToFavourites(trip: FavouriteTrip, context: Context) {
        FavouritesDatabase.addFavourite(context, trip)
        Toast.makeText(context, "Adding '".plus(trip.origin).plus(" -> ").plus(trip.destination).plus("' to favourites"), Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFavourites(trip: FavouriteTrip, context: Context) {
        FavouritesDatabase.removeFavourite(context, trip)
        Toast.makeText(context, "Removing '".plus(trip.origin).plus(" -> ").plus(trip.destination).plus("' from favourites"), Toast.LENGTH_SHORT).show()
    }


}