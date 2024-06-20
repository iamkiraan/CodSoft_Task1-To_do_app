package com.example.taskflow.appBarFragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskflow.Calender.CalenderAdapter
import com.example.taskflow.Calender.CalenderDataClass
import com.example.taskflow.databinding.DialogAddEventBinding
import com.example.taskflow.databinding.FragmentCalenderBinding
import java.util.*

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CalenderAdapter
    private var eventList = ArrayList<CalenderDataClass>()
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedDate: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedPreferences = requireContext().getSharedPreferences("EventPrefs", Context.MODE_PRIVATE)

        binding.eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CalenderAdapter(this, eventList)
        binding.eventRecyclerView.adapter = adapter

        binding.calenderEv.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            openEventDialog()
        }

        loadEvents()
        deletePastEvents()

        return view
    }

    private fun openEventDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val dialogBinding = DialogAddEventBinding.inflate(inflater)
        builder.setView(dialogBinding.root)

        builder.setPositiveButton("Add Event") { dialog, _ ->
            val eventName = dialogBinding.eventNameEditText.text.toString()
            if (eventName.isNotEmpty()) {
                val event = CalenderDataClass(selectedDate, eventName)
                eventList.add(event)
                adapter.notifyItemInserted(eventList.size - 1)
                saveEvents()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }


    private fun saveEvents() {
        val editor = sharedPreferences.edit()
        val eventSet = eventList.map { "${it.date}::${it.text}" }.toSet()
        editor.putStringSet("events", eventSet)
        editor.apply()
    }


    private fun loadEvents() {
        val eventSet = sharedPreferences.getStringSet("events", emptySet())
        eventList.clear()
        eventSet?.forEach {
            val parts = it.split("::")
            if (parts.size == 2) {
                val date = parts[0].toLong()
                val text = parts[1]
                eventList.add(CalenderDataClass(date, text))
            }
        }
        adapter.notifyDataSetChanged()
    }


    private fun deletePastEvents() {
        val currentTime = System.currentTimeMillis()
        eventList = eventList.filter { it.date >= currentTime } as ArrayList<CalenderDataClass>
        saveEvents()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun deleteEventFromSharedPreferences(event: CalenderDataClass) {
        val eventSet = sharedPreferences.getStringSet("events", emptySet())?.toMutableSet()
        eventSet?.remove("${event.date}::${event.text}")
        sharedPreferences.edit().putStringSet("events", eventSet).apply()
    }
}
