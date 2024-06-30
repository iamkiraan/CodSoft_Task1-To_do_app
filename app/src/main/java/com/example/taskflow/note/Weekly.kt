package com.example.taskflow.note

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskflow.R
import com.example.taskflow.Weekly.WeeklyAdapter
import com.example.taskflow.Weekly.WeeklyDataClass
import com.example.taskflow.databinding.ActivityWeeklyBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Weekly : AppCompatActivity() {

    private val taskList = mutableListOf<WeeklyDataClass>()
    private val doneTaskList = mutableListOf<WeeklyDataClass>()
    private lateinit var taskAdapter: WeeklyAdapter
    private lateinit var doneTaskAdapter: WeeklyAdapter
    private lateinit var binding: ActivityWeeklyBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("WeeklyTasks", Context.MODE_PRIVATE)

        loadTasks()

        taskAdapter = WeeklyAdapter(taskList, object : WeeklyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: WeeklyDataClass, isChecked: Boolean) {
                handleTaskCheckChange(task, isChecked)
            }
        })

        doneTaskAdapter = WeeklyAdapter(doneTaskList, object : WeeklyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: WeeklyDataClass, isChecked: Boolean) {
                handleTaskCheckChange(task, isChecked)
            }
        })

        binding.recyclerWeeklyTask.adapter = taskAdapter
        binding.recyclerWeeklyTask.layoutManager = LinearLayoutManager(this)

        binding.recyclerWeeklyDone.adapter = doneTaskAdapter
        binding.recyclerWeeklyDone.layoutManager = LinearLayoutManager(this)

        binding.addTaskWeekly.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editTextTask: EditText = view.findViewById(R.id.editTextTask)
        val buttonAdd: Button = view.findViewById(R.id.buttonAddTask)

        builder.setView(view)
        val dialog = builder.create()

        buttonAdd.setOnClickListener {
            val taskName = editTextTask.text.toString().trim()
            if (taskName.isNotEmpty()) {
                val task = WeeklyDataClass(taskName, false)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                saveTasks()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun handleTaskCheckChange(task: WeeklyDataClass, isChecked: Boolean) {
        taskList.remove(task)
        taskAdapter.notifyDataSetChanged()
        task.isDone = isChecked
        if (isChecked) {
            doneTaskList.add(task)
            doneTaskAdapter.notifyItemInserted(doneTaskList.size - 1)
        } else {
            taskList.add(task)
            taskAdapter.notifyItemInserted(taskList.size - 1)
        }
        doneTaskAdapter.notifyDataSetChanged()
        saveTasks()
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        editor.putString("taskList", gson.toJson(taskList))
        editor.putString("doneTaskList", gson.toJson(doneTaskList))
        editor.apply()
    }

    private fun loadTasks() {
        val taskListJson = sharedPreferences.getString("taskList", null)
        val doneTaskListJson = sharedPreferences.getString("doneTaskList", null)

        if (!taskListJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<WeeklyDataClass>>() {}.type
            taskList.addAll(gson.fromJson(taskListJson, type))
        }

        if (!doneTaskListJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<WeeklyDataClass>>() {}.type
            doneTaskList.addAll(gson.fromJson(doneTaskListJson, type))
        }
    }
}
