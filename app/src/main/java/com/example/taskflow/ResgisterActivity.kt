package com.example.taskflow

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.appBarFragments.MainActivity
import com.example.taskflow.databinding.ActivityResgisterBinding
import com.example.taskflow.starting_activities.Register

class ResgisterActivity : AppCompatActivity() {
    private var _binding: ActivityResgisterBinding? = null
    private val binding get() = _binding!!
    val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResgisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        binding.arrow.setOnClickListener {
            val intent = Intent(this@ResgisterActivity, Register::class.java)
            startActivity(intent)
            finish()
        }


        binding.username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateUsername()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateEmail()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.createAccount.setOnClickListener {
            if (validateUsername() && validateEmail() && validatePassword()) {
                Toast.makeText(this, "Account creation successful", Toast.LENGTH_SHORT).show()
                saveUserData(
                    binding.username.text.toString().trim(),
                    binding.email.text.toString().trim(),
                    binding.password.text.toString()
                )
                val intent = Intent(this@ResgisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveUserData(username: String, email: String, password: String) {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", username)
            putString("email", email)
            putString("password", password)
            putBoolean("isRegistered", true)
            apply()
        }
    }

    private fun validateUsername(): Boolean {
        val username = binding.username.text.toString().trim()
        return if (username.isEmpty() || username.length <= 2) {
            binding.username.setBackgroundResource(R.drawable.incorrect_edittext)
            false
        } else {
            binding.username.setBackgroundResource(R.drawable.correct_edittext)
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.email.text.toString().trim()
        return if (!email.matches(emailRegex)) {
            binding.email.setBackgroundResource(R.drawable.incorrect_edittext)
            false
        } else {
            binding.email.setBackgroundResource(R.drawable.correct_edittext)
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.password.text.toString().trim()
        return if (password.isEmpty() || password.length <= 8) {
            binding.password.setBackgroundResource(R.drawable.incorrect_edittext)
            false
        } else {
            binding.password.setBackgroundResource(R.drawable.correct_edittext)
            true
        }
    }
}
