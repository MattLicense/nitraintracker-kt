package com.licensem.nitraintracker.listeners

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.xml.Service
import android.view.WindowManager
import com.licensem.nitraintracker.model.xml.CallingPoint
import com.licensem.nitraintracker.model.xml.Destination
import com.licensem.nitraintracker.util.AnkoAdapter
import org.jetbrains.anko.*

class ServiceClickListener(private val service: Service, val origin: String, val destination: String)
    : View.OnClickListener {

    override fun onClick(view: View?) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(view?.context)
        val popupLayout: LinearLayout = layoutInflater.inflate(R.layout.service_popup, null) as LinearLayout

        var callingPoints: ListView = popupLayout.findViewById<ListView>(R.id.calling_points_list)
        var serviceDestination: Destination = service.destination!!
        var originCallingPoint = CallingPoint(origin, service.departTime!!.time!!, service.getEstimatedTime()!!)
        var destCallingPoint = CallingPoint(serviceDestination.name!!, serviceDestination.scheduledArrival!!, serviceDestination.estimatedArrival!!)

        var fullCallingPoints: List<CallingPoint> = listOf(originCallingPoint).plus(service.callingPoints).plus(destCallingPoint)

        callingPoints.adapter = AnkoAdapter({ fullCallingPoints }) {
            index, callingPoints, view ->
            var callingPoint = callingPoints[index]!!

            relativeLayout {
                fitsSystemWindows = true
                if(callingPoint.name == destination) {
                    backgroundColor = Color.LTGRAY
                }
                textView(callingPoint.name).lparams {
                    id = R.id.calling_point
                }.lparams {
                    width = wrapContent
                    height = dip(32)
                    gravity = Gravity.CENTER_VERTICAL
                    alignParentLeft()
                    centerVertically()
                    setPadding(dip(10), dip(0), dip(10), dip(0))
                }
                textView(callingPoint.scheduledDeparture) {
                    id = R.id.scheduled_time
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11F)
                }.lparams {
                    rightOf(R.id.calling_point)
                    alignParentRight()
                    setPadding(dip(10), dip(0), dip(10), dip(0))
                    gravity = Gravity.RIGHT
                    textAlignment = Gravity.RIGHT
                    width = wrapContent
                    height = dip(16)
                }
                textView {
                    id = R.id.estimated_time
                    if(callingPoint.scheduledDeparture == callingPoint.estimatedDeparture) {
                        text = "On Time"
                        textColor = Color.BLACK
                    } else {
                        text = "Estimated: " + callingPoint.estimatedDeparture
                        textColor = Color.RED
                    }
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11F)
                }.lparams {
                    rightOf(R.id.calling_point)
                    below(R.id.scheduled_time)
                    alignParentRight()
                    gravity = Gravity.RIGHT
                    textAlignment = Gravity.RIGHT
                    setPadding(dip(10), dip(0), dip(10), dip(0))
                    width = wrapContent
                    height = dip(16)
                }
            }
        }

        var servicePopup = PopupWindow(popupLayout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true)
        servicePopup.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dim the background while the service pop up is active
        val container = servicePopup.contentView.parent as View
        val wm = view?.context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.6f
        wm.updateViewLayout(container, p)

    }

}