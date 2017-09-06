package com.licensem.nitraintracker.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class AnkoAdapter<out T>(
        itemFactory: () -> List<T>,
        private val viewFactory: Context.(index: Int, items: List<T>,view: View?) -> View
): BaseAdapter() {
    private val items: List<T> by lazy { itemFactory() }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup?): View {
        return viewGroup!!.context.viewFactory(index, items, view)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): T {
        return items[index]
    }

    override fun getItemId(index: Int): Long {
        return (items[index] as Any).hashCode().toLong() + (index.toLong() * Int.MAX_VALUE)
    }
}
