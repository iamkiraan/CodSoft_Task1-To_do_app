package com.example.taskflow.task

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.R
import com.example.taskflow.databinding.ActivityAddTaskBinding
import java.util.*

class AddTask : AppCompatActivity() {

    private var _binding: ActivityAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        // Set onClickListeners for priority buttons
        binding.low.setOnClickListener { onPriorityButtonClick(it) }
        binding.medium.setOnClickListener { onPriorityButtonClick(it) }
        binding.high.setOnClickListener { onPriorityButtonClick(it) }

        // Set onClickListeners for other options
        binding.bulletOtpions.setOnClickListener { onBulletOptionsClick() }
        binding.colorOptions.setOnClickListener { onColorOptionsClick() }
        binding.savedOtpions.setOnClickListener { onSaveOptionsClick() }
        binding.attachFileOptions.setOnClickListener { onAttachFileOptionsClick() }

        // Set onClickListeners for setting date and selecting folder
        binding.SetDate.setOnClickListener { onSetDateClick() }
        binding.setFolder.setOnClickListener { onSelectFolderClick() }

        // Set onClickListener for creating task
        binding.createTask.setOnClickListener { onCreateTaskClick() }

        // Handle back arrow click
        binding.arrowFolder.setOnClickListener { onBackPressed() }
    }

    private fun onPriorityButtonClick(clickedButton: View) {
        // Reset all buttons to unselected state
        binding.low.isSelected = false
        binding.medium.isSelected = false
        binding.high.isSelected = false

        // Set selected state to clicked button
        clickedButton.isSelected = true

        // Change background based on selection
        when (clickedButton.id) {
            R.id.low -> {
                binding.low.setBackgroundResource(R.drawable.priority_check)
                binding.medium.setBackgroundResource(R.drawable.button_blue)
                binding.high.setBackgroundResource(R.drawable.button_red)
            }
            R.id.medium -> {
                binding.low.setBackgroundResource(R.drawable.green_button)
                binding.medium.setBackgroundResource(R.drawable.priority_check)
                binding.high.setBackgroundResource(R.drawable.button_red)
            }
            R.id.high -> {
                binding.low.setBackgroundResource(R.drawable.green_button)
                binding.medium.setBackgroundResource(R.drawable.button_blue)
                binding.high.setBackgroundResource(R.drawable.priority_check)
            }
        }
    }

    private fun onBulletOptionsClick() {
        // TODO: Implement bullet options functionality
        Toast.makeText(this, "Bullet options clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onColorOptionsClick() {
        // TODO: Implement color options functionality
        Toast.makeText(this, "Color options clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onSaveOptionsClick() {
        // TODO: Implement save options functionality
        Toast.makeText(this, "Save options clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onAttachFileOptionsClick() {
        // TODO: Implement attach file functionality
        Toast.makeText(this, "Attach file options clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onSetDateClick() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            Toast.makeText(this, "Selected Date: $selectedDay/${selectedMonth + 1}/$selectedYear", Toast.LENGTH_SHORT).show()
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun onSelectFolderClick() {

        Toast.makeText(this, "Select folder clicked", Toast.LENGTH_SHORT).show()
    }

    private fun onCreateTaskClick() {
        val taskDescription = binding.tasksIn.text.toString()
        if (taskDescription.isEmpty()) {
            Toast.makeText(this, "Please enter a task description", Toast.LENGTH_SHORT).show()
            return
        }

        val priority = when {
            binding.low.isSelected -> "Low"
            binding.medium.isSelected -> "Medium"
            binding.high.isSelected -> "High"
            else -> "None"
        }

        Toast.makeText(this, "Task created with priority: $priority", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
