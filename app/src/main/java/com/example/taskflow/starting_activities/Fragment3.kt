package com.example.taskflow.starting_activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskflow.ResgisterActivity
import com.example.taskflow.databinding.Fragment3Binding

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {

    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = Fragment3Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.getStarted.setOnClickListener {
            val intent = Intent(activity, Register::class.java)
            startActivity(intent)
        }

        return view
    }

}
