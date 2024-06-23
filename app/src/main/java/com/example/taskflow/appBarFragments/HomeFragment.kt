package com.example.taskflow.appBarFragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.DayDataClass
import com.example.taskflow.FolderCreation.FolderAdapter
import com.example.taskflow.FolderCreation.FolderDataClass
import com.example.taskflow.R
import com.example.taskflow.WeekAdapter
import com.example.taskflow.databinding.FragmentHomeBinding
import com.example.taskflow.home.FolderViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var weekAdapter: WeekAdapter
    private lateinit var weekList: ArrayList<DayDataClass>

    // Folder variables
    private lateinit var folderList: ArrayList<FolderDataClass>
    private lateinit var folderAdapter: FolderAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var folderViewModel: FolderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        folderViewModel = ViewModelProvider(this).get(FolderViewModel::class.java)

        // Load folder list from preferences and initialize adapter
        folderList = loadFolderListFromPreferences()
        folderAdapter = FolderAdapter(requireContext(), folderList) { position ->
            onFolderDeleted(position)
        }

        // Setup RecyclerView for folders
        recyclerView = binding.recyclerViewHome
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = folderAdapter

        // Initialize and set the week list
        weekList = getCurrentWeek()
        weekAdapter = WeekAdapter(weekList)
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RecyclerView.adapter = weekAdapter

        // Check for week change
        fixedRateTimer("weekChangeTimer", true, 0L, 24 * 60 * 60 * 1000) {
            Handler(Looper.getMainLooper()).post {
                if (isNewWeek()) {
                    weekList.clear()
                    weekList.addAll(getCurrentWeek())
                    weekAdapter.notifyDataSetChanged()
                }
            }
        }

        // Get user info and set greeting
        getUserInfo()
        val greet = greeting()
        binding.greeting.text = greet

        // Setup notification button toggle
        var count = 0
        binding.notification.setOnClickListener {
            count++
            if (count % 2 != 0) {
                binding.notification.setBackgroundResource(R.drawable.baseline_notifications_24)
            } else {
                binding.notification.setBackgroundResource(R.drawable.outline_notifications_24)
            }
        }
    }

    private fun loadFolderListFromPreferences(): ArrayList<FolderDataClass> {
        val sharedPreferences = requireContext().getSharedPreferences(
            "com.example.taskflow.PREFERENCE_FILE_KEY",
            Context.MODE_PRIVATE
        )
        val json = sharedPreferences.getString("folders", null)
        return if (json != null) {
            val type = object : TypeToken<List<FolderDataClass>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }
    }

    private fun greeting(): String {
        val currentTime = Calendar.getInstance().get(Calendar.AM_PM)
        return when (currentTime) {
            Calendar.AM -> "Good Morning!"
            Calendar.PM -> "Good Afternoon!"
            else -> "Good Evening!"
        }
    }

    private fun getUserInfo() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("username", "")
        binding.personName.text = name
    }

    private fun getCurrentWeek(): ArrayList<DayDataClass> {
        val weekList = ArrayList<DayDataClass>()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        for (i in 0..6) {
            val dayName = dayFormat.format(calendar.time)
            val dayDate = dateFormat.format(calendar.time)
            val isCurrentDay = calendar.get(Calendar.DAY_OF_WEEK) == currentDay
            weekList.add(DayDataClass(dayName, dayDate, isCurrentDay))
            calendar.add(Calendar.DAY_OF_WEEK, 1)
        }
        return weekList
    }

    private fun isNewWeek(): Boolean {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time
        return !weekList[0].dayDate.equals(SimpleDateFormat("dd", Locale.getDefault()).format(startOfWeek))
    }

    private fun onFolderDeleted(position: Int) {
        folderList.removeAt(position)
        folderAdapter.notifyItemRemoved(position)
        saveFolderList()
    }

    private fun saveFolderList() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "com.example.taskflow.PREFERENCE_FILE_KEY",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("folders", Gson().toJson(folderList))
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
