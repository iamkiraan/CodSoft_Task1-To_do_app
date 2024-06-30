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
import com.example.taskflow.Yearly.YearlyAdapter
import com.example.taskflow.Yearly.YearlyDataClass
import com.example.taskflow.databinding.ActivityYearlyBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Yearly : AppCompatActivity() {

    private val taskList = mutableListOf<YearlyDataClass>()
    private val doneTaskList = mutableListOf<YearlyDataClass>()
    private lateinit var taskAdapter: YearlyAdapter
    private lateinit var doneTaskAdapter: YearlyAdapter
    private lateinit var binding: ActivityYearlyBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYearlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("YearlyTasks", Context.MODE_PRIVATE)

        taskAdapter = YearlyAdapter(taskList, object : YearlyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: YearlyDataClass, isChecked: Boolean) {
                handleTaskCheckChange(task, isChecked)
            }
        })

        doneTaskAdapter = YearlyAdapter(doneTaskList, object : YearlyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: YearlyDataClass, isChecked: Boolean) {
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

        loadTasks()
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
                val task = YearlyDataClass(taskName, false)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                saveTasks()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun handleTaskCheckChange(task: YearlyDataClass, isChecked: Boolean) {
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
            val type = object : TypeToken<MutableList<YearlyDataClass>>() {}.type
            taskList.addAll(gson.fromJson(taskListJson, type))
        }

        if (!doneTaskListJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<YearlyDataClass>>() {}.type
            doneTaskList.addAll(gson.fromJson(doneTaskListJson, type))
        }
    }
}
