package com.example.taskflow.note

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

class Monthly : AppCompatActivity() {

    private val taskList = mutableListOf<MonthlyDataClass>()
    private val doneTaskList = mutableListOf<MonthlyDataClass>()
    private lateinit var taskAdapter: MonthlyAdapter
    private lateinit var doneTaskAdapter: MonthlyAdapter
    private lateinit var binding: ActivityMonthlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
}
