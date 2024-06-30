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
import com.example.taskflow.note.Daily
import com.example.taskflow.note.Monthly
import com.example.taskflow.note.Weekly
import com.example.taskflow.note.Yearly

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

        //daily work
        binding.dailyWork.setOnClickListener{
            val intent = Intent(requireContext(),Daily::class.java)
            startActivity(intent)
        }

        // weekly work
        binding.weeklyWork.setOnClickListener{
            val intent = Intent(requireContext(),Weekly::class.java)
            startActivity(intent)
        }

        //monthly work
        binding.monthlyWork.setOnClickListener{
            val intent = Intent(requireContext(), Monthly::class.java)
            startActivity(intent)
        }

        //yearly work
        binding.yearlyWork.setOnClickListener{
            val intent = Intent(requireContext(), Yearly::class.java)
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
