package com.example.taskflow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeekAdapter(private val weekList: List<DayDataClass>) : RecyclerView.Adapter<WeekAdapter.WeekViewHolder>() {

    class WeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayName: TextView = itemView.findViewById(R.id.dayName)
        val dayDate: TextView = itemView.findViewById(R.id.dayDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_days, parent, false)
        return WeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val dayData = weekList[position]
        holder.dayName.text = dayData.dayName
        holder.dayDate.text = dayData.dayDate

        // Set selected state based on isCurrentDay
        holder.itemView.isSelected = dayData.isCurrentDay

        // Apply background and text color state
        holder.itemView.setBackgroundResource(R.drawable.current_day_background)
        holder.dayName.setTextColor(holder.itemView.resources.getColorStateList(R.drawable.current_day_text_color))
        holder.dayDate.setTextColor(holder.itemView.resources.getColorStateList(R.drawable.current_day_text_color))
    }




    override fun getItemCount(): Int {
        return weekList.size
    }
}
