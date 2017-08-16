package com.licensem.nitraintracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.licensem.nitraintracker.model.FavouriteTrip

object FavouritesDatabase {

    private var database: SQLiteDatabase? = null

    private fun getDatabase(context: Context) : SQLiteDatabase {
        if(database == null) {
            database = context.openOrCreateDatabase("nitraintracker.db", Context.MODE_PRIVATE, null)
            database!!.execSQL("CREATE TABLE IF NOT EXISTS favourites (origin VARCHAR(128), destination VARCHAR(128))")
        }

        return database!!
    }

    fun getFavourites(context: Context) : List<FavouriteTrip> {
        val favouritesCursor = getDatabase(context).rawQuery("SELECT DISTINCT origin, destination FROM favourites", null)

        var favourites: MutableList<FavouriteTrip> = mutableListOf()

        while(favouritesCursor.moveToNext()) {
            favourites.add(FavouriteTrip(origin = favouritesCursor.getString(0), destination = favouritesCursor.getString(1)))
        }

        favouritesCursor.close()

        return favourites
    }

    fun addFavourite(context: Context, trip: FavouriteTrip) {
        val row = ContentValues()
        row.put("origin", trip.origin)
        row.put("destination", trip.destination)
        getDatabase(context).insertOrThrow("favourites", null, row)
    }

    fun removeFavourite(context: Context, trip: FavouriteTrip) {
        getDatabase(context)
            .delete("favourites", "origin=? and destination=?", arrayOf(trip.origin, trip.destination))
    }

    fun exists(context: Context, trip: FavouriteTrip) : Boolean {
        var favouriteCheck = getDatabase(context)
            .rawQuery("SELECT DISTINCT origin, destination FROM favourites WHERE origin=? AND destination=?", arrayOf(trip.origin, trip.destination))
        var exists = favouriteCheck.count > 0
        favouriteCheck.close()

        return exists
    }

}