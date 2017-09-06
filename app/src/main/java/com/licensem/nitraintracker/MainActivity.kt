package com.licensem.nitraintracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.licensem.nitraintracker.views.MainView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.setContentView

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
        val favourites = FavouritesDatabase.getFavourites(applicationContext)
        info("User has ${favourites.count()} favourite routes")
        MainView().apply{ this.favourites = favourites.toTypedArray() }.setContentView(this)
    }

}
