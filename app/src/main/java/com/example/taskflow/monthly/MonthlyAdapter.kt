package com.example.taskflow.monthly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R

class MonthlyAdapter(
    private val taskList: MutableList<MonthlyDataClass>,
    private val listener: OnTaskCheckedChangeListener
) : RecyclerView.Adapter<MonthlyAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTask.text = task.taskName
        holder.checkBoxTask.isChecked = task.isDone
        holder.checkBoxTask.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                listener.onTaskCheckedChanged(task, isChecked)
            }
        }
    }

    override fun getItemCount(): Int = taskList.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxTask: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        val textViewTask: TextView = itemView.findViewById(R.id.taskName)
    }

    interface OnTaskCheckedChangeListener {
        fun onTaskCheckedChanged(task: MonthlyDataClass, isChecked: Boolean)
    }
}
