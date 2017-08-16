package com.licensem.nitraintracker

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.licensem.nitraintracker.model.FavouriteTrip
import com.licensem.nitraintracker.util.FavouritesAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFavourites()
    }

    override fun onResume() {
        super.onResume()
        getFavourites()
    }

    private fun getFavourites() {
        var favourites = FavouritesDatabase.getFavourites(this)

        if(favourites.isEmpty()) {
            var favouritesText = findViewById(R.id.favourites_text)
            favouritesText.visibility = View.INVISIBLE
        }

        var favouritesView = findViewById(R.id.favourites_list) as ListView
        favouritesView.adapter = FavouritesAdapter(this, android.R.layout.list_content, favourites.toTypedArray())
    }

    fun startSearch(view: View) {
        var intent: Intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

}
