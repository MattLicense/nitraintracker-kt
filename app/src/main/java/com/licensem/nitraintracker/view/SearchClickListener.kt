package com.licensem.nitraintracker.view

import android.view.View
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.json.Station
import com.licensem.nitraintracker.model.xml.Service
import com.licensem.nitraintracker.model.xml.StationBoard
import com.licensem.nitraintracker.tasks.ApiCallTask
import com.licensem.nitraintracker.tasks.StationListTask
import com.licensem.nitraintracker.util.ServiceListAdapter

class SearchClickListener : View.OnClickListener {

    override fun onClick(view: View?) {
        val parentView: View = view?.parent as View
        
        val originSpinner = parentView.findViewById<Spinner>(R.id.originSelector)
        val destinationSpinner = parentView.findViewById<Spinner>(R.id.destinationSelector)

        var originName: String = originSpinner?.selectedItem.toString()
        var destName: String = destinationSpinner?.selectedItem.toString()

        if(originName == destName) {
            Toast.makeText(view?.context,"Please make sure the origin and destination are different",Toast.LENGTH_SHORT).show()
        } else {
            var origin: Station = StationListTask().execute().get()!!.getStationByName(originName)!!

            var originBoard: StationBoard = ApiCallTask().execute(origin.code).get()!!
            var services: List<Service> = originBoard.findTrainsTo(destName)

            var resultsView: ListView = parentView.findViewById(R.id.result_view)
            resultsView.adapter = ServiceListAdapter(parentView.context, android.R.layout.list_content, services.toTypedArray()).destination(destName)
            resultsView.visibility = View.VISIBLE

            if(services.isEmpty()) {
                Toast.makeText(
                        view?.context,
                        "No direct services found from ".plus(originName)
                                .plus(" to ")
                                .plus(destName),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}