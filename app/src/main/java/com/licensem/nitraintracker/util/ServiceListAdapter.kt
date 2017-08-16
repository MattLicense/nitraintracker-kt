package com.licensem.nitraintracker.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.xml.Service
import com.licensem.nitraintracker.view.ServiceClickListener

class ServiceListAdapter(context: Context?, resource: Int, private val services: Array<Service>?) : ArrayAdapter<Service>(context, resource, services) {

    var destination: String = ""

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rowView: View = inflater.inflate(R.layout.service_view, parent, false)

        val topRow: TextView = rowView.findViewById(R.id.firstLine)
        val secondRow: TextView = rowView.findViewById(R.id.secondLine)

        val service: Service = services!![position]

        val isCallingPoint: Boolean
        isCallingPoint = service.callingPoints.filter { c -> c.name.equals(destination) }.isNotEmpty()

        var arrivalTime: String
        if(isCallingPoint) {
            arrivalTime = service.callingPoints.filter { c -> c.name.equals(destination) }.first().getEstimatedTime()
        } else {
            arrivalTime = service.destination!!.getEstimatedTime()
        }

        topRow.text = "Service to: ".plus(service.destination!!.name)
        secondRow.text = "Leaving from platform ".plus(service.platform!!.platform)
                                                 .plus(" at ")
                                                 .plus(service.departTime!!.getFormattedTime())
                                                 .plus(". Arriving at ")
                                                 .plus(arrivalTime)

        rowView.setOnClickListener(ServiceClickListener(service, destination))
        System.setProperty("clearResults", "true")

        return rowView
    }

    fun destination(destination: String) : ServiceListAdapter {
        this.destination = destination
        return this
    }

}