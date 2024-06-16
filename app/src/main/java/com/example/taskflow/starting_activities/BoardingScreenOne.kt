package com.example.taskflow.starting_activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.taskflow.R
import com.example.taskflow.databinding.ActivityBoardingScreenOneBinding
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class BoardingScreenOne : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingScreenOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = viewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager1.adapter = adapter
        binding.indicator.setViewPager2(binding.viewPager1)

    }
}
