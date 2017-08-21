package com.licensem.nitraintracker.listeners

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.Spinner
import com.licensem.nitraintracker.FavouritesDatabase
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.FavouriteTrip
import org.jetbrains.anko.toast

class FavouriteToggleListener : CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val parentView = buttonView?.parent?.parent as View
        var originSpinner = parentView.findViewById<Spinner>(R.id.originSelector)
        var destinationSpinner = parentView.findViewById<Spinner>(R.id.destinationSelector)

        val favourite = FavouriteTrip(originSpinner.selectedItem.toString(), destinationSpinner.selectedItem.toString())
        val favouriteExists: Boolean = FavouritesDatabase.exists(parentView?.context, favourite)

        if(isChecked && !favouriteExists) {
            addToFavourites(favourite, parentView?.context)
        } else if(!isChecked && favouriteExists) {
            removeFromFavourites(favourite, parentView?.context)
        }
    }

    private fun addToFavourites(trip: FavouriteTrip, context: Context) {
        FavouritesDatabase.addFavourite(context, trip)
        context.toast("Adding '".plus(trip.origin).plus(" -> ").plus(trip.destination).plus("' to favourites"))
    }

    private fun removeFromFavourites(trip: FavouriteTrip, context: Context) {
        FavouritesDatabase.removeFavourite(context, trip)
        context.toast("Removing '".plus(trip.origin).plus(" -> ").plus(trip.destination).plus("' from favourites"))
    }


}