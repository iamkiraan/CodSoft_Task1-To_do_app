package com.example.taskflow.addTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.example.taskflow.note.Task

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onTaskCheckChanged: (Task, Boolean) -> Unit,
    private val onDeleteTask: (Task) -> Unit,
    private val isActiveTask: Boolean
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDelete: ImageView = itemView.findViewById(R.id.taskdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskCheckBox.isChecked = !isActiveTask

        // Handle checkbox changes
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTaskCheckChanged(task, isChecked)
        }

        // Handle delete button click
        holder.taskDelete.setOnClickListener {
            onDeleteTask(task)
        }

        // Disable the checkbox and task name editing for completed tasks
        if (!isActiveTask) {
            holder.taskCheckBox.isEnabled = false
            holder.taskName.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    // Function to add a task
    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    // Function to remove a task
    fun removeTask(task: Task) {
        val position = tasks.indexOf(task)
        if (position != -1) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
