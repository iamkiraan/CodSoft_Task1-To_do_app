package com.example.taskflow.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.taskflow.Daily.TaskPreferences
import com.example.taskflow.Daily.Task
import com.google.gson.Gson


class ClearTaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        clearTasks(context!!)
    }

    private fun clearTasks(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("TaskFlowPrefs", Context.MODE_PRIVATE)
        val gson = Gson()

        // Clear task lists
        val editor = sharedPreferences.edit()
        editor.putString(TaskPreferences.KEY_ACTIVE_TASKS, gson.toJson(emptyList<Task>()))
        editor.putString(TaskPreferences.KEY_COMPLETED_TASKS, gson.toJson(emptyList<Task>()))
        editor.apply()

        Log.d("ClearTasksReceiver", "Tasks cleared at midnight")
    }
}
