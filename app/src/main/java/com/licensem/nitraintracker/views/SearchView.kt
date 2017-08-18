package com.licensem.nitraintracker.views

import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.SearchActivity
import com.licensem.nitraintracker.listeners.FavouriteToggleListener
import com.licensem.nitraintracker.listeners.SearchClickListener
import com.licensem.nitraintracker.listeners.StationSelectorListener
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SearchView : AnkoComponent<SearchActivity> {

    private var stationNames: Array<String> = arrayOf()

    override fun createView(ui: AnkoContext<SearchActivity>) = with(ui) {
        verticalLayout {
            toolbar {
                backgroundColor = ContextCompat.getColor(context, com.licensem.nitraintracker.R.color.colorPrimaryDark)
                textView("NI Train Tracker") {
                    textColor = ContextCompat.getColor(context, android.R.color.white)
                    setTextSize(TypedValue.COMPLEX_UNIT_PT, 11F)
                }.lparams {
                    width = wrapContent
                    gravity = Gravity.LEFT
                }

                themedToggleButton {
                    id = R.id.favourite_button
                    backgroundDrawable = ContextCompat.getDrawable(context, com.licensem.nitraintracker.R.drawable.favourite_star_bg)
                    setOnCheckedChangeListener(FavouriteToggleListener())
                    text = ""
                    textSize = 0F
                }.lparams {
                    width = dip(28)
                    height = dip(28)
                    rightMargin = dip(10)
                    gravity = Gravity.RIGHT
                }
            }.lparams{
                width = matchParent
                padding = dip(0)
            }

            var originSpinner: Spinner? = null
            var destinationSpinner: Spinner? = null

            textView("Select Origin:") {
                setTextSize(TypedValue.COMPLEX_UNIT_PT, 11F)
            }.lparams {
                gravity = Gravity.BOTTOM
                height = dip(32)
                leftMargin = dip(3)
                rightMargin = dip(3) 
            }

            originSpinner = spinner {
                id = com.licensem.nitraintracker.R.id.originSelector
                adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, stationNames)
                onItemSelectedListener = StationSelectorListener()
                backgroundColor = ContextCompat.getColor(context, R.color.colorSelectors)
            }.lparams {
                width = dip(332)
                leftMargin = dip(3)
                rightMargin = dip(3)
            }

            textView("Select Destination:") {
                setTextSize(TypedValue.COMPLEX_UNIT_PT, 11F)
            }.lparams {
                gravity = Gravity.BOTTOM
                height = dip(32)
                leftMargin = dip(3)
                rightMargin = dip(3)
            }

            imageView {
                id = com.licensem.nitraintracker.R.id.swap_button
                imageResource = com.licensem.nitraintracker.R.drawable.swap_arrows
                onClick {
                    var originIdx = originSpinner!!.selectedItemPosition
                    var destinationIdx = destinationSpinner!!.selectedItemPosition

                    destinationSpinner!!.setSelection(originIdx)
                    originSpinner!!.setSelection(destinationIdx)
                }
            }.lparams {
                gravity = Gravity.RIGHT
                topMargin = dip(-32)
                height = dip(32)
                leftMargin = dip(3)
                rightMargin = dip(3)
            }

            destinationSpinner = spinner {
                id = com.licensem.nitraintracker.R.id.destinationSelector
                adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, stationNames)
                onItemSelectedListener = StationSelectorListener()
                backgroundColor = ContextCompat.getColor(context, R.color.colorSelectors)
            }.lparams {
                width = dip(332)
                leftMargin = dip(3)
                rightMargin = dip(3)
            }

            button("Search") {
                id = com.licensem.nitraintracker.R.id.search_button
                height = dip(60)
                width = dip(332)
                setOnClickListener(SearchClickListener())
                backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
                textColor = ContextCompat.getColor(context, R.color.colorAccent)
                setTextSize(TypedValue.COMPLEX_UNIT_PT, 11F)
            }.lparams {
                topMargin = dip(10)
                gravity = Gravity.CENTER
                leftMargin = dip(3)
                rightMargin = dip(3)
            }

            listView {
                id = com.licensem.nitraintracker.R.id.result_view
            }.lparams {
                topMargin = dip(15)
                leftMargin = dip(3)
                rightMargin = dip(3)
            }
        }

    }

    fun stations(stations: List<String>) : SearchView {
        this.stationNames = stations.toTypedArray()
        return this
    }

    fun stations(stations: Array<String>) : SearchView {
        this.stationNames = stations
        return this
    }

}