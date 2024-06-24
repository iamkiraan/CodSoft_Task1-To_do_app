package com.example.taskflow.note

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskPreferences {
    private const val PREFS_NAME = "task_prefs"
    private const val ACTIVE_TASKS_KEY = "active_tasks"
    private const val COMPLETED_TASKS_KEY = "completed_tasks"

    private val gson = Gson()

    fun getActiveTasks(context: Context): List<Task> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(ACTIVE_TASKS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Task>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun getCompletedTasks(context: Context): List<Task> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(COMPLETED_TASKS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Task>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun saveTasks(context: Context, activeTasks: List<Task>, completedTasks: List<Task>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(ACTIVE_TASKS_KEY, gson.toJson(activeTasks))
            putString(COMPLETED_TASKS_KEY, gson.toJson(completedTasks))
            apply()
        }
    }
}
