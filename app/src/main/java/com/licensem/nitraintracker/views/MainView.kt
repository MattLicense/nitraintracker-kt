package com.licensem.nitraintracker.views

import android.util.TypedValue
import android.view.Gravity
import com.licensem.nitraintracker.MainActivity
import com.licensem.nitraintracker.SearchActivity
import com.licensem.nitraintracker.model.FavouriteTrip
import com.licensem.nitraintracker.util.AnkoAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainView : AnkoComponent<MainActivity> {

    var favourites: Array<FavouriteTrip> = arrayOf()

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            padding = dip(2)

            button("Search Train Times") {
                onClick { startActivity<SearchActivity>() }
                width = dip(332)
            }.lparams {
                gravity = Gravity.CENTER
                setMargins(dip(0), dip(5), dip(0), dip(5))
            }

            textView("Favourite routes") {
                setTextSize(TypedValue.COMPLEX_UNIT_PT, 14F)
            }.lparams {
                setMargins(dip(0), dip(5), dip(0), dip(5))
            }

            if(favourites.isEmpty()) {
                textView("Favourite routes will appear here") {
                    setTextSize(TypedValue.COMPLEX_UNIT_PT, 21F)
                }.lparams {
                    setMargins(dip(0), dip(5), dip(0), dip(5))
                }
            } else {
                listView {
                    adapter = AnkoAdapter<FavouriteTrip>({ favourites.asList() }) {
                        index, favourites , view ->
                        val favourite = favourites!![index]

                        relativeLayout {
                            textView(favourite.origin) {
                                id = com.licensem.nitraintracker.R.id.favourite_origin
                                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                            }.lparams {
                                height = dip(32)
                                width = wrapContent
                                gravity = Gravity.CENTER
                                margin = dip(5)
                            }

                            imageView {
                                id = com.licensem.nitraintracker.R.id.favourite_right_arrow
                                imageResource = com.licensem.nitraintracker.R.drawable.arrow_right
                            }.lparams {
                                width = dip(32)
                                height = dip(32)
                                margin = dip(5)
                                centerHorizontally()
                                alignParentTop()
                            }

                            textView(favourite.destination) {
                                id = com.licensem.nitraintracker.R.id.favourite_dest
                                setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                            }.lparams {
                                height = dip(32)
                                width = wrapContent
                                gravity = Gravity.CENTER
                                margin = dip(5)
                                alignParentTop()
                                alignParentEnd()
                            }

                            onClick {
                                startActivity<SearchActivity>("origin" to favourite.origin, "destination" to favourite.destination)
                            }
                        }
                    }
                }.lparams {
                    setMargins(dip(0), dip(5), dip(0), dip(5))
                }
            }
        }
    }

}