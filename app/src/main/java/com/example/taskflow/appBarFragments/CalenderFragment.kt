package com.example.taskflow.appBarFragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.Calender.AddEvent

import com.example.taskflow.Calender.CalenderAdapter
import com.example.taskflow.Calender.CalenderDataClass

import com.example.taskflow.databinding.FragmentCalenderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventList: ArrayList<CalenderDataClass>
    private lateinit var eventAdapter: CalenderAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView and load events
        recyclerView = binding.eventRecyclerView
        loadEvents()

        // Setup RecyclerView
        eventAdapter = CalenderAdapter(this, eventList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = eventAdapter

        // Handle calendar date click
        binding.calenderEv.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val selectedDate = calendar.timeInMillis
            navigateToCreateEvent(selectedDate)
        }


        return view
    }

    private fun loadEvents() {
        val sharedPreferences = requireContext().getSharedPreferences("EventPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("events", null)
        eventList = if (json != null) {
            val type = object : TypeToken<List<CalenderDataClass>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }
    }

    private fun navigateToCreateEvent(selectedDate: Long) {
        val intent = Intent(requireContext(), AddEvent::class.java)
        intent.putExtra("selectedDate", selectedDate)
        startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_EVENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val eventName = data?.getStringExtra("eventName")
            val selectedDate = data?.getLongExtra("selectedDate", 0)

            if (!eventName.isNullOrBlank() && selectedDate != null) {
                val newEvent = CalenderDataClass(selectedDate, eventName)
                eventList.add(newEvent)
                eventAdapter.notifyItemInserted(eventList.size - 1)
                saveEvents()
                Log.d("CalenderFragment", "New event added: $newEvent")
            } else {
                Log.e("CalenderFragment", "New event data is null")
            }
        }
    }


    private fun saveEvents() {
        val sharedPreferences = requireContext().getSharedPreferences("EventPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("events", Gson().toJson(eventList))
        editor.apply()
    }

    fun deleteEventFromSharedPreferences(event: CalenderDataClass) {
        eventList.remove(event)
        saveEvents()
        eventAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CREATE_EVENT_REQUEST_CODE = 101
    }
}
