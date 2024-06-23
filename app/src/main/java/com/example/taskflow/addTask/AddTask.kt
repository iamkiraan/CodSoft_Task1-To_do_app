package com.example.taskflow.addTask

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.R
import com.example.taskflow.databinding.ActivityAddTaskBinding

class AddTask : AppCompatActivity() {

    private var _binding: ActivityAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        // Reference the buttons from the binding
        val button1 = binding.low
        val button2 = binding.medium
        val button3 = binding.high

        // Set onClickListener for each button
        button1.setOnClickListener { onButtonClick(it) }
        button2.setOnClickListener { onButtonClick(it) }
        button3.setOnClickListener { onButtonClick(it) }
    }

    private fun onButtonClick(clickedButton: View) {
        // Determine which button was clicked
        val button1 = binding.low
        val button2 = binding.medium
        val button3 = binding.high

        // Reset all buttons to unselected state
        button1.isSelected = false
        button2.isSelected = false
        button3.isSelected = false


        clickedButton.isSelected = true


        when (clickedButton.id) {
            R.id.low -> {
                button1.setBackgroundResource(R.drawable.priority_check)
                button2.setBackgroundResource(R.drawable.button_blue)
                button3.setBackgroundResource(R.drawable.button_red)
            }
            R.id.medium -> {
                button1.setBackgroundResource(R.drawable.green_button)
                button2.setBackgroundResource(R.drawable.priority_check)
                button3.setBackgroundResource(R.drawable.button_red)
            }
            R.id.high -> {
                button1.setBackgroundResource(R.drawable.green_button)
                button2.setBackgroundResource(R.drawable.button_blue)
                button3.setBackgroundResource(R.drawable.priority_check)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
