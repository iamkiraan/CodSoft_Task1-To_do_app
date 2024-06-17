package com.example.taskflow.starting_activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskflow.R
import com.example.taskflow.appBarFragments.MainActivity
import com.example.taskflow.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Starting : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        animateText(binding.TODO)

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            navigateNextScreen()
        }
    }

    private fun animateText(textView: TextView) {
        textView.alpha = 0f
        textView.animate()
            .alpha(0f)
            .setDuration(1000)
            .withEndAction {
                textView.animate()
                    .alpha(1f)
                    .setDuration(2000)
                    .start()
            }
            .start()
    }

    private fun navigateNextScreen() {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isRegistered = sharedPref.getBoolean("isRegistered", false)

        val nextActivityClass = if (isRegistered) {
            MainActivity::class.java
        } else {
            BoardingScreenOne::class.java
        }

        val intent = Intent(this@Starting, nextActivityClass)
        startActivity(intent)
        finish()
    }
}
