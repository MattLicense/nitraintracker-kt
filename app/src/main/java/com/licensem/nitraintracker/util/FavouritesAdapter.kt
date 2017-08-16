package com.licensem.nitraintracker.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.SearchActivity
import com.licensem.nitraintracker.model.FavouriteTrip

class FavouritesAdapter(context: Context?, resource: Int, private val favourites: Array<FavouriteTrip>?) : ArrayAdapter<FavouriteTrip>(context, resource, favourites) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rowView: View = inflater.inflate(R.layout.favourite_view, parent, false)
        var originText: TextView = rowView.findViewById(R.id.favourite_origin)
        var destinationText: TextView = rowView.findViewById(R.id.favourite_dest)

        val favourite = favourites!![position]

        originText.text = favourite.origin
        destinationText.text = favourite.destination

        rowView.setOnClickListener(FavouriteClickListener(context!!, favourite.origin, favourite.destination))

        return rowView
    }

    private class FavouriteClickListener(val context: Context, val origin: String, val dest: String) : View.OnClickListener {

        override fun onClick(view: View?) {
            var intent: Intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("origin", origin)
            intent.putExtra("destination", dest)
            context.startActivity(intent)
        }

    }

}