package com.example.taskflow.starting_activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskflow.R
import com.example.taskflow.ResgisterActivity
import com.example.taskflow.SignInActivity
import com.example.taskflow.databinding.ActivityRegisterBinding
import com.example.taskflow.databinding.ActivityResgisterBinding

class Register : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.getStarted.setOnClickListener{
            val intent = Intent(this@Register,ResgisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signIn.setOnClickListener{
            val intent = Intent(this@Register,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}