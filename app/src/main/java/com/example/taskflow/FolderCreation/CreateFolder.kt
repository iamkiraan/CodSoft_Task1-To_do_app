package com.example.taskflow.FolderCreation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.taskflow.R
import com.example.taskflow.databinding.CreateFolderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CreateFolder : AppCompatActivity() {

    private var _binding: CreateFolderBinding? = null
    private val binding get() = _binding!!

    private var selectedColor: String = "#FFFFFF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = CreateFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        initializeClick()


        binding.createFolder.setOnClickListener {
            val folderName = binding.foldername.text.toString().trim()
            if (folderName.isNotEmpty()) {
                val newFolder = FolderDataClass(folderName,0, selectedColor)
                saveFolder(newFolder)
                Toast.makeText(this, "Folder created!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Folder name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveFolder(folder: FolderDataClass) {
        val sharedPreferences = getSharedPreferences("com.example.taskflow.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val gson = Gson()
        val folderList = loadFolderList().toMutableList()
        folderList.add(folder)
        val json = gson.toJson(folderList)
        sharedPreferences.edit().putString("folders", json).apply()
    }
    private fun loadFolderList(): List<FolderDataClass> {
        val sharedPreferences = getSharedPreferences("com.example.taskflow.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("folders", null)
        return if (json != null) {
            val type = object : TypeToken<List<FolderDataClass>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun initializeClick() {
        binding.circle.setOnClickListener {
            handleColorSelection(R.color.circle.toString())
        }

        binding.circle2.setOnClickListener {
            handleColorSelection(R.color.circle2.toString())
        }

        binding.circle3.setOnClickListener {
            handleColorSelection(R.color.circle3.toString())
        }
        binding.circle4.setOnClickListener {
            handleColorSelection(R.color.circle4.toString())
        }
        binding.circle5.setOnClickListener {
            handleColorSelection(R.color.circle5.toString())
        }
        binding.circle6.setOnClickListener {
            handleColorSelection(R.color.circle6.toString())
        }
        binding.circle7.setOnClickListener {
            handleColorSelection(R.color.circle7.toString())
        }
    }



    private fun handleColorSelection(colorCode: String) {
        selectedColor = colorCode
    }
}
