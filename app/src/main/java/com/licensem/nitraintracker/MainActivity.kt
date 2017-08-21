package com.licensem.nitraintracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.licensem.nitraintracker.views.MainView
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFavourites()
    }

    override fun onResume() {
        super.onResume()
        getFavourites()
    }

    private fun getFavourites() {
        var favourites = FavouritesDatabase.getFavourites(applicationContext)
        info("User has ${favourites.count()} favourite routes")
        MainView().apply{ this.favourites = favourites.toTypedArray() }.setContentView(this)
    }

}
