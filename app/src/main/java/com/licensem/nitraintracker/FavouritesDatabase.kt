package com.licensem.nitraintracker

import android.content.Context
import com.licensem.nitraintracker.model.FavouriteTrip
import org.jetbrains.anko.db.insertOrThrow
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

object FavouritesDatabase {

    private val TO_FAVOURITES_LIST
            = rowParser { origin: String, destination: String -> FavouriteTrip(origin, destination) }

    private val Context.database: DbOpenHelper
        get() = DbOpenHelper.getInstance(applicationContext)

    @Synchronized
    fun getFavourites(context: Context) : List<FavouriteTrip> {
        return context.database.use {
            select("favourites").distinct().exec { parseList(TO_FAVOURITES_LIST) }
        }
    }

    @Synchronized
    fun addFavourite(context: Context, trip: FavouriteTrip) = context.database.use {
        insertOrThrow("favourites", "origin" to trip.origin, "destination" to trip.destination)
    }

    @Synchronized
    fun removeFavourite(context: Context, trip: FavouriteTrip) = context.database.use {
        delete("favourites", "origin=? and destination=?", arrayOf(trip.origin, trip.destination))
    }


    @Synchronized
    fun exists(context: Context, trip: FavouriteTrip) : Boolean {
        return context.database.use {
            select("favourites", "origin","destination")
                .distinct()
                .whereArgs("(origin = {origin}) and (destination = {destination})", "origin" to trip.origin, "destination" to trip.destination)
                .exec { count > 0 }
        }
    }

}