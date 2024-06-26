package com.example.taskflow.FolderCreation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.taskflow.R
import com.example.taskflow.databinding.CreateFolderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CreateFolder : AppCompatActivity() {

    private var _binding: CreateFolderBinding? = null
    private val binding get() = _binding!!

    private var selectedColorResId: Int = R.color.white

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = CreateFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeClick()
        binding.arrowFolder.setOnClickListener {
            finish()
        }

        binding.createFolder.setOnClickListener {
            val folderName = binding.foldername.text.toString()
            if (folderName.isNotEmpty()) {
                val newFolder = FolderDataClass(folderName, selectedColorResId)
                saveFolder(newFolder)
                val resultIntent = Intent()
                resultIntent.putExtra("newFolder", Gson().toJson(newFolder))
                resultIntent.putExtra("selectedColor", selectedColorResId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun saveFolder(newFolder: FolderDataClass) {
        val sharedPreferences = getSharedPreferences("com.example.taskflow.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("folders", null)
        val folderList: ArrayList<FolderDataClass> = if (json != null) {
            val type = object : TypeToken<List<FolderDataClass>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }
        folderList.add(newFolder)
        val editor = sharedPreferences.edit()
        editor.putString("folders", Gson().toJson(folderList))
        editor.apply()
    }

    private fun initializeClick() {
        binding.circle.setOnClickListener {
            val randomColor = getRandomColor()
            binding.foldername.setTextColor(ContextCompat.getColor(this, randomColor))
            (binding.circle as ImageView).setColorFilter(ContextCompat.getColor(this, randomColor))
            selectedColorResId = randomColor
        }
    }

    private fun getRandomColor(): Int {
        val colors = listOf(
            R.color.red, R.color.blue, R.color.green,
            R.color.black, R.color.purple, R.color.orange,R.color.boarding3,R.color.boarding2,R.color.steel_blue,R.color.circle7
        )
        return colors.random()
    }
}
