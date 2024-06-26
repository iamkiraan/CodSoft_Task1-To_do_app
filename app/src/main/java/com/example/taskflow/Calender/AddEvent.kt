package com.example.taskflow.Calender

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.databinding.ActivityAddEventBinding

class AddEvent : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedDate = intent.getLongExtra("selectedDate", 0)

        binding.createEvent.setOnClickListener {
            val eventName = binding.EventName.text.toString()
            if (eventName.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("eventName", eventName)
                resultIntent.putExtra("selectedDate", selectedDate)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
