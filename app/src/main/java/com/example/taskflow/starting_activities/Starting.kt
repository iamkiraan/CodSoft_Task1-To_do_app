package com.example.taskflow.starting_activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskflow.MainActivity
import com.example.taskflow.R
import com.example.taskflow.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Starting : AppCompatActivity() {
    private lateinit var binding :  ActivitySplashScreenBinding
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
                val intent = Intent(this@Starting,BoardingScreenOne::class.java)
            startActivity(intent)
            finish()



        }
    }

    private fun animateText(textView: TextView) {
            textView.alpha = 0f
            textView.animate()
                .alpha(1f)
                .setDuration(2000)
                .withEndAction {
                    textView.animate()
                        .alpha(0f)
                        .setDuration(2000)
                        .start()
                }
                .start()



    }
}