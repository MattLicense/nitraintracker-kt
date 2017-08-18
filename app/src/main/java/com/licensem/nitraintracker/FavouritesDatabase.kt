package com.licensem.nitraintracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.licensem.nitraintracker.model.FavouriteTrip

object FavouritesDatabase {

    val Context.database: DbOpenHelper
        get() = DbOpenHelper.getInstance(applicationContext)

    @Synchronized
    fun getFavourites(context: Context) : List<FavouriteTrip> {
        var favourites: MutableList<FavouriteTrip> = mutableListOf()

        context.database.use {
            var cursor = rawQuery("SELECT DISTINCT origin, destination FROM favourites", null)
            while(cursor.moveToNext()) {
                favourites.add(FavouriteTrip(origin = cursor.getString(0), destination = cursor.getString(1)))
            }
            cursor.close()
        }

        return favourites
    }

    @Synchronized
    fun addFavourite(context: Context, trip: FavouriteTrip) {
        val row = ContentValues()
        row.put("origin", trip.origin)
        row.put("destination", trip.destination)
        context.database.use { insertOrThrow("favourites", null, row) }
    }

    @Synchronized
    fun removeFavourite(context: Context, trip: FavouriteTrip) {
        context.database.use { delete("favourites", "origin=? and destination=?", arrayOf(trip.origin, trip.destination)) }
    }

    @Synchronized
    fun exists(context: Context, trip: FavouriteTrip) : Boolean {
        var exists = false
        context.database.use {
            var cursor = rawQuery("SELECT DISTINCT origin, destination FROM favourites WHERE origin=? AND destination=?", arrayOf(trip.origin, trip.destination))
            exists = cursor.count > 0
            cursor.close()
        }

        return exists
    }

}