package com.example.taskflow.Calender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.example.taskflow.appBarFragments.CalenderFragment
import java.util.Date
import java.util.Locale

class CalenderAdapter(
    private val context: Context,
    private val eventList: ArrayList<CalenderDataClass>
) : RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_on_calender, parent, false)
        return CalenderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        val event = eventList[position]
        holder.eventName.text = event.text
        holder.eventDate.text = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date(event.date)
        )
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class CalenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.EventName)
        val eventDate: TextView = itemView.findViewById(R.id.eventDate)
    }
}
