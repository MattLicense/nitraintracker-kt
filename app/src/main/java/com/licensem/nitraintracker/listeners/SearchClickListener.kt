package com.licensem.nitraintracker.listeners

import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ListView
import android.widget.Spinner
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.json.Station
import com.licensem.nitraintracker.model.xml.Service
import com.licensem.nitraintracker.model.xml.StationBoard
import com.licensem.nitraintracker.tasks.ApiCallTask
import com.licensem.nitraintracker.tasks.StationListTask
import com.licensem.nitraintracker.util.AnkoAdapter
import org.jetbrains.anko.*

class SearchClickListener : View.OnClickListener, AnkoLogger {

    override fun onClick(view: View?) {
        val parentView: View = view?.parent as View
        
        val originSpinner: Spinner = parentView.findViewById(R.id.originSelector)
        val destinationSpinner: Spinner = parentView.findViewById(R.id.destinationSelector)

        val originName: String = originSpinner.selectedItem.toString()
        val destName: String = destinationSpinner.selectedItem.toString()

        if(originName == destName) {
            view.context.toast("Please make sure the origin and destination are different")
        } else {
            val origin: Station = StationListTask().execute().get()!!.getStationByName(originName)!!

            val originBoard: StationBoard = ApiCallTask().execute(origin.code).get()!!
            val services: List<Service> = originBoard.findTrainsTo(destName)

            val resultsView = parentView.findViewById<ListView>(R.id.result_view)

            resultsView.adapter = AnkoAdapter({ services }) {
                index, _, _ ->
                    val service = services[index]
                    val isCallingPoint: Boolean = service.callingPoints.any { (name) -> name == destName }

                    val arrivalTime = if(isCallingPoint) {
                        service.callingPoints.first { (name) -> name == destName }.getEstimatedTime()
                    } else {
                        service.destination!!.getEstimatedTime()
                    }

                    relativeLayout {
                        id = R.id.service_result
                        textView("Service to: ".plus(service.destination!!.name)) {
                            id = R.id.service_title
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                        }.lparams {
                            height = dip(32)
                            gravity = Gravity.CENTER_VERTICAL
                            alignParentTop()
                            alignParentLeft()
                            bottomMargin = dip(16)
                            setPadding(dip(3), dip(0), dip(3), dip(0))
                        }
                        textView("Leaving from platform ".plus(service.platform)
                                .plus(" at ")
                                .plus(service.getEstimatedTime())
                                .plus(", arriving at ")
                                .plus(arrivalTime)) {
                            id = R.id.service_detail
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F)
                        }.lparams {
                            height = dip(24)
                            gravity = Gravity.CENTER_VERTICAL
                            alignParentBottom()
                            alignParentLeft()
                            setPadding(dip(3), dip(0), dip(3), dip(0))
                        }
                        setOnClickListener(ServiceClickListener(service, originName, destName))
                    }
            }
            resultsView.tag = "$originName-$destName"

            view.context.toast(if(services.isEmpty()) {
                "No direct services found from $originName to $destName"
            } else {
                "Found ${services.size} services from $originName to $destName"
            })
        }
    }

}