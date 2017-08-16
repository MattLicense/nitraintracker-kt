package com.licensem.nitraintracker.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import com.licensem.nitraintracker.R
import com.licensem.nitraintracker.model.xml.Service
import com.licensem.nitraintracker.util.CallingPointsAdapter
import android.view.WindowManager
import com.licensem.nitraintracker.model.xml.CallingPoint
import com.licensem.nitraintracker.model.xml.Destination


class ServiceClickListener(private val service: Service, val destination: String) : View.OnClickListener {

    override fun onClick(view: View?) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(view?.context)
        val popupLayout: LinearLayout = layoutInflater.inflate(R.layout.service_popup, null) as LinearLayout

        var servicePopup = PopupWindow(popupLayout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)

        var callingPoints: ListView = popupLayout.findViewById<ListView>(R.id.calling_points_list)
        var serviceDestination: Destination = service.destination!!
        var destCallingPoint = CallingPoint(serviceDestination.name!!, serviceDestination.scheduledArrival!!, serviceDestination.estimatedArrival!!)

        callingPoints.adapter = CallingPointsAdapter(
            popupLayout.context,
            android.R.layout.list_content,
            service.callingPoints.toTypedArray().plus(destCallingPoint)
        ).destination(destination)

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