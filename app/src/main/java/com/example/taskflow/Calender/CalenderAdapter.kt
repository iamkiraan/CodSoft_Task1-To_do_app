package com.example.taskflow.Calender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.example.taskflow.appBarFragments.CalenderFragment
import java.text.SimpleDateFormat
import java.util.*

class CalenderAdapter(
    private val fragment: CalenderFragment,
    private var eventList: ArrayList<CalenderDataClass>
) : RecyclerView.Adapter<CalenderAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.event_on_calender, parent, false)
        return EventViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventDetails = eventList[position]

        // Format date to show only date part
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(eventDetails.date))

        holder.eventName.text = eventDetails.text
        holder.eventDate.text = formattedDate

        // Handle more options click
        holder.moreOptions.setOnClickListener {
            showPopupMenu(holder.moreOptions, position)
        }
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventName: TextView = itemView.findViewById(R.id.eventTask)
        var eventDate: TextView = itemView.findViewById(R.id.eventDate)
        var moreOptions: ImageView = itemView.findViewById(R.id.moreOptions)
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_event_optin, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_delete -> {
                    deleteEvent(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun deleteEvent(position: Int) {
        val deletedEvent = eventList[position]
        eventList.removeAt(position)
        notifyItemRemoved(position)
        fragment.deleteEventFromSharedPreferences(deletedEvent)
    }
}
