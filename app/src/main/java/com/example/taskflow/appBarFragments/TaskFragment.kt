package com.example.taskflow.appBarFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskflow.R
import com.example.taskflow.addTask.AddTask
import com.example.taskflow.databinding.FragmentHomeBinding
import com.example.taskflow.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {


    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.addTask.setOnClickListener {

            val intent = Intent(requireContext(), AddTask::class.java)
            startActivity(intent)
        }
        return view

    }

    }
