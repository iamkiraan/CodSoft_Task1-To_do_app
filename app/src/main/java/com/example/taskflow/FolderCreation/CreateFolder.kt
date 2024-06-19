package com.example.taskflow.FolderCreation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                val newFolder = FolderDataClass(folderName, selectedColorResId, 0)
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
            handleColorSelection(R.color.circle)
        }
        binding.circle2.setOnClickListener {
            handleColorSelection(R.color.circle2)
        }
        binding.circle3.setOnClickListener {
            handleColorSelection(R.color.circle3)
        }
        binding.circle4.setOnClickListener {
            handleColorSelection(R.color.circle4)
        }
        binding.circle5.setOnClickListener {
            handleColorSelection(R.color.circle5)
        }
        binding.circle6.setOnClickListener {
            handleColorSelection(R.color.circle6)
        }
        binding.circle7.setOnClickListener {
            handleColorSelection(R.color.circle7)
        }
    }

    private fun handleColorSelection(colorResId: Int) {
        selectedColorResId = colorResId
    }
}
