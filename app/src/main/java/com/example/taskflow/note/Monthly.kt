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
import com.example.taskflow.databinding.ActivityMonthlyBinding
import com.example.taskflow.monthly.MonthlyAdapter
import com.example.taskflow.monthly.MonthlyDataClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Monthly : AppCompatActivity() {

    private val taskList = mutableListOf<MonthlyDataClass>()
    private val doneTaskList = mutableListOf<MonthlyDataClass>()
    private lateinit var taskAdapter: MonthlyAdapter
    private lateinit var doneTaskAdapter: MonthlyAdapter
    private lateinit var binding: ActivityMonthlyBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MonthlyPrefs", Context.MODE_PRIVATE)

        taskAdapter = MonthlyAdapter(taskList, object : MonthlyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: MonthlyDataClass, isChecked: Boolean) {
                handleTaskCheckChange(task, isChecked)
            }
        })

        doneTaskAdapter = MonthlyAdapter(doneTaskList, object : MonthlyAdapter.OnTaskCheckedChangeListener {
            override fun onTaskCheckedChanged(task: MonthlyDataClass, isChecked: Boolean) {
                handleTaskCheckChange(task, isChecked)
            }
        })

        binding.recyclerDailyTask.adapter = taskAdapter
        binding.recyclerDailyTask.layoutManager = LinearLayoutManager(this)

        binding.recyclerDailyDone.adapter = doneTaskAdapter
        binding.recyclerDailyDone.layoutManager = LinearLayoutManager(this)

        binding.addTaskMonthly.setOnClickListener {
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
                val task = MonthlyDataClass(taskName, false)
                taskList.add(task)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                saveTasks()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun handleTaskCheckChange(task: MonthlyDataClass, isChecked: Boolean) {
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
        val taskJson = gson.toJson(taskList)
        val doneTaskJson = gson.toJson(doneTaskList)
        sharedPreferences.edit().apply {
            putString("taskList", taskJson)
            putString("doneTaskList", doneTaskJson)
            apply()
        }
    }

    private fun loadTasks() {
        val taskJson = sharedPreferences.getString("taskList", "")
        val doneTaskJson = sharedPreferences.getString("doneTaskList", "")

        if (!taskJson.isNullOrEmpty()) {
            val taskType = object : TypeToken<MutableList<MonthlyDataClass>>() {}.type
            val tasks: MutableList<MonthlyDataClass> = gson.fromJson(taskJson, taskType)
            taskList.addAll(tasks)
            taskAdapter.notifyDataSetChanged()
        }

        if (!doneTaskJson.isNullOrEmpty()) {
            val doneTaskType = object : TypeToken<MutableList<MonthlyDataClass>>() {}.type
            val doneTasks: MutableList<MonthlyDataClass> = gson.fromJson(doneTaskJson, doneTaskType)
            doneTaskList.addAll(doneTasks)
            doneTaskAdapter.notifyDataSetChanged()
        }
    }
}
