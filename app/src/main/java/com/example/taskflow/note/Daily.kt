package com.example.taskflow.note


import android.content.Context

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskflow.Daily.Task
import com.example.taskflow.R
import com.example.taskflow.Daily.TaskAdapter

import com.example.taskflow.databinding.ActivityDailyBinding
import com.google.gson.Gson


class Daily : AppCompatActivity() {
    private var _binding: ActivityDailyBinding? = null
    private val binding get() = _binding!!

    private lateinit var activeTaskAdapter: TaskAdapter
    private lateinit var completedTaskAdapter: TaskAdapter
    private lateinit var activeTasks: MutableList<Task>
    private lateinit var completedTasks: MutableList<Task>
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDailyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("TaskFlowPrefs", Context.MODE_PRIVATE)




        // shared preference bata load hunxa
        activeTasks = TaskPreferences.getActiveTasks(this).toMutableList()
        completedTasks = TaskPreferences.getCompletedTasks(this).toMutableList()

        // adatpter initialize gareko
        activeTaskAdapter = TaskAdapter(activeTasks, { task, isChecked -> onTaskCheckChanged(task, isChecked) }, { task -> onDeleteTask(task, true) }, true)
        completedTaskAdapter = TaskAdapter(completedTasks, { task, isChecked -> onTaskCheckChanged(task, isChecked) }, { task -> onDeleteTask(task, false) }, false)


        binding.recyclerDailyTask.layoutManager = LinearLayoutManager(this)
        binding.recyclerDailyTask.adapter = activeTaskAdapter

        binding.recyclerDailyDone.layoutManager = LinearLayoutManager(this)
        binding.recyclerDailyDone.adapter = completedTaskAdapter

        // click listener
        binding.addTaskOrange.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val editTextTask = dialogView.findViewById<EditText>(R.id.editTextTask)
        val buttonAddTask = dialogView.findViewById<Button>(R.id.buttonAddTask)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .create()

        buttonAddTask.setOnClickListener {
            val taskText = editTextTask.text.toString()
            if (taskText.isNotEmpty()) {
                addTask(taskText)
                dialog.dismiss()
            } else {
                editTextTask.error = "Task cannot be empty"
            }
        }

        dialog.show()
    }

    private fun addTask(taskName: String) {
        val task = Task(taskName)
        activeTaskAdapter.addTask(task)
        saveTasksToPreferences()
    }

    private fun onTaskCheckChanged(task: Task, isChecked: Boolean) {
        if (isChecked) {
            activeTaskAdapter.removeTask(task)
            completedTaskAdapter.addTask(task)
        } else {
            completedTaskAdapter.removeTask(task)
            activeTaskAdapter.addTask(task)
        }
        saveTasksToPreferences()
    }

    private fun onDeleteTask(task: Task, isActiveTask: Boolean) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Delete the task if the user confirms
                if (isActiveTask) {
                    activeTaskAdapter.removeTask(task)
                    activeTasks.remove(task)
                } else {
                    completedTaskAdapter.removeTask(task)
                    completedTasks.remove(task)
                }
                saveTasksToPreferences()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun saveTasksToPreferences() {
        TaskPreferences.saveTasks(this, activeTasks, completedTasks)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
