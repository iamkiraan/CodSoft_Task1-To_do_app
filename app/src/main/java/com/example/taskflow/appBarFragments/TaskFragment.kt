package com.example.taskflow.appBarFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskflow.R
import com.example.taskflow.addTask.AddTask
import com.example.taskflow.CustomSpinnerAdapter
import com.example.taskflow.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the addTask button click listener
        binding.addTask.setOnClickListener {
            val intent = Intent(requireContext(), AddTask::class.java)
            startActivity(intent)
        }


        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = CustomSpinnerAdapter(requireContext(), R.layout.spinner_item, spinnerItems)
        binding.taskSpinner.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
