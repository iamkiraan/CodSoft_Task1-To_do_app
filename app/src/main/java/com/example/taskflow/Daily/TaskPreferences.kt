package com.example.taskflow.Daily

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskPreferences {
     const val KEY_ACTIVE_TASKS = "active_tasks"
     const val KEY_COMPLETED_TASKS = "completed_tasks"

    fun getActiveTasks(context: Context): List<Task> {
        val sharedPreferences = getSharedPreferences(context)
        val activeTasksJson = sharedPreferences.getString(KEY_ACTIVE_TASKS, null)
        val type = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(activeTasksJson, type) ?: emptyList()
    }

    fun getCompletedTasks(context: Context): List<Task> {
        val sharedPreferences = getSharedPreferences(context)
        val completedTasksJson = sharedPreferences.getString(KEY_COMPLETED_TASKS, null)
        val type = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson(completedTasksJson, type) ?: emptyList()
    }

    fun saveTasks(context: Context, activeTasks: List<Task>, completedTasks: List<Task>) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        editor.putString(KEY_ACTIVE_TASKS, gson.toJson(activeTasks))
        editor.putString(KEY_COMPLETED_TASKS, gson.toJson(completedTasks))
        editor.apply()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("TaskFlowPrefs", Context.MODE_PRIVATE)
    }
}
