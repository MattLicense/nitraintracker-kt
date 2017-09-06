package com.licensem.nitraintracker.listeners

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.xml.CallingPoint
import com.licensem.nitraintracker.model.xml.Service
import com.licensem.nitraintracker.util.AnkoAdapter
import org.jetbrains.anko.*

class ServiceClickListener(private val service: Service, val origin: String, val destination: String)
    : View.OnClickListener {

    override fun onClick(view: View?) {
        val popupLayout: LinearLayout = LayoutInflater.from(view?.context).inflate(R.layout.service_popup, null) as LinearLayout

        val callingPoints: ListView = popupLayout.findViewById(R.id.calling_points_list)
        val originCallingPoint = CallingPoint(origin, service.departTime!!.time, service.getEstimatedTime()!!.replace(":", ""))

        val destCallingPoint = CallingPoint.createFromDestination(service.destination!!)

        val fullCallingPoints: List<CallingPoint> = listOf(originCallingPoint).plus(service.callingPoints).plus(destCallingPoint)

        var destIdx = fullCallingPoints.size

        callingPoints.adapter = AnkoAdapter({ fullCallingPoints }) { index, _, _ ->
            val callingPoint = fullCallingPoints[index]

            if(destIdx >= index) {
                relativeLayout {
                    fitsSystemWindows = true
                    if (callingPoint.name == destination) {
                        backgroundColor = Color.LTGRAY
                        destIdx = index
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
                    textView(callingPoint.getScheduledTime()) {
                        id = R.id.scheduled_time
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11F)
                    }.lparams {
                        rightOf(R.id.calling_point)
                        alignParentRight()
                        setPadding(dip(10), dip(0), dip(10), dip(0))
                        gravity = Gravity.END
                        textAlignment = Gravity.END
                        width = wrapContent
                        height = dip(16)
                    }
                    textView {
                        id = R.id.estimated_time
                        if (callingPoint.scheduledDeparture == callingPoint.estimatedDeparture) {
                            text = "On Time"
                            textColor = Color.BLACK
                        } else {
                            text = "Estimated: ${callingPoint.getEstimatedTime()}"
                            textColor = Color.RED
                        }
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11F)
                    }.lparams {
                        rightOf(R.id.calling_point)
                        below(R.id.scheduled_time)
                        alignParentRight()
                        gravity = Gravity.END
                        textAlignment = Gravity.END
                        setPadding(dip(10), dip(0), dip(10), dip(0))
                        width = wrapContent
                        height = dip(16)
                    }
                }
            } else {
                relativeLayout {  }
            }
        }

        val servicePopup = PopupWindow(popupLayout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        servicePopup.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dim the background while the service pop up is active
        val container = servicePopup.contentView.parent as View
        val p = (container.layoutParams as WindowManager.LayoutParams).apply {
            this.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            this.dimAmount = 0.6f
        }
        (view?.context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).updateViewLayout(container, p)

    }

}