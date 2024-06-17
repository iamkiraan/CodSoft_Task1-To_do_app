package com.example.taskflow

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.appBarFragments.MainActivity
import com.example.taskflow.databinding.ActivitySignInBinding
import com.example.taskflow.starting_activities.Register

class SignInActivity : AppCompatActivity() {
    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        email = sharedPref.getString("email", null) ?: ""
        password = sharedPref.getString("password", null) ?: ""

        binding.arrow.setOnClickListener{
            val intent = Intent(this@SignInActivity, Register::class.java)
            startActivity(intent)
            finish()
        }

        binding.emailS.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateEmail()
            }
        })

        binding.passwordS.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }
        })

        binding.signinAccount.setOnClickListener {
            if (validateEmail() && validatePassword()) {
                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateEmail(): Boolean {
        val enteredEmail = binding.emailS.text.toString().trim()
        return if (enteredEmail == email) {
            binding.emailS.setBackgroundResource(R.drawable.correct_edittext)
            true
        } else {
            binding.emailS.setBackgroundResource(R.drawable.incorrect_edittext)
            false
        }
    }

    private fun validatePassword(): Boolean {
        val enteredPassword = binding.passwordS.text.toString().trim()
        return if (enteredPassword == password) {
            binding.passwordS.setBackgroundResource(R.drawable.correct_edittext)
            true
        } else {
            binding.passwordS.setBackgroundResource(R.drawable.incorrect_edittext)
            false
        }
    }
}
