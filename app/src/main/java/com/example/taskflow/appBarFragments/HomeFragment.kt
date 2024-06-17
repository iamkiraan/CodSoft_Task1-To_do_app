package com.example.taskflow.appBarFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskflow.R
import com.example.taskflow.databinding.FragmentHomeBinding
import java.util.Calendar
import java.util.Calendar.AM
import java.util.Calendar.PM

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserInfo()
       val greet = greeting()
        binding.greeting.text = greet
        var count =0
        binding.notification.setOnClickListener{
            count++

            if((count%2)!=0) {
                binding.notification.setBackgroundResource(R.drawable.baseline_notifications_24)
            }else{
                binding.notification.setBackgroundResource(R.drawable.outline_notifications_24)
            }

        }
    }

    private fun greeting():String {
        val currentTime = Calendar.getInstance().get(Calendar.AM_PM)
        return when (currentTime) {
             AM-> "Good Morning!"
            PM -> "Good Afternoon!"
            else -> "Good Evening!"
        }
    }


    private fun getUserInfo() {
        val username = ""
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("username", username)
        binding.personName.text = name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
