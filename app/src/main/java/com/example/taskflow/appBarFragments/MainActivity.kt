package com.example.taskflow.appBarFragments

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskflow.R
import com.example.taskflow.appBarFragments.HomeFragment
import com.example.taskflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        Replace(HomeFragment())
        //fragment wala kaam
        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.HOME -> Replace(HomeFragment())
                R.id.CREATE -> Replace(TaskFragment())
                R.id.CALENDER -> Replace(CalenderFragment())
                R.id.FOLDER -> Replace(FolderFragment())

                else -> {
                }
            }
            true

        }
    }


    private fun Replace(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}