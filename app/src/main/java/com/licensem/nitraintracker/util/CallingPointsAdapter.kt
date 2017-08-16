package com.licensem.nitraintracker.util

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.xml.CallingPoint

class CallingPointsAdapter(context: Context?, resource: Int, private val callingPoints: Array<CallingPoint>?) : ArrayAdapter<CallingPoint>(context, resource, callingPoints) {

    var destination: String = ""

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rowView: View = inflator.inflate(R.layout.calling_points_view, parent, false)

        val callingPointText: TextView = rowView.findViewById(R.id.calling_point)
        val schDeptTime: TextView = rowView.findViewById(R.id.scheduled_time)
        val estDepTime: TextView = rowView.findViewById(R.id.estimated_time)

        val callingPoint: CallingPoint = callingPoints!![position]
        callingPointText.text = callingPoint.name
        schDeptTime.text = callingPoint.getScheduledTime()

        if(callingPoint.name.equals(destination)) {
            rowView.setBackgroundColor(Color.LTGRAY)
        }

        if(!callingPoint.estimatedDeparture.equals(callingPoint.scheduledDeparture)) {
            estDepTime.text = "Estimated: ".plus(callingPoint.getEstimatedTime())
            estDepTime.setTextColor(Color.RED)
        } else {
            estDepTime.text = "On Time"
            estDepTime.setTextColor(Color.BLACK)
        }

        return rowView
    }

    fun destination(destination: String) : CallingPointsAdapter {
        this.destination = destination
        return this
    }

}