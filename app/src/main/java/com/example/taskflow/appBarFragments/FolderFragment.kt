package com.example.taskflow.appBarFragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.FolderCreation.CreateFolder
import com.example.taskflow.FolderCreation.FolderAdapter
import com.example.taskflow.FolderCreation.FolderDataClass
import com.example.taskflow.R
import com.example.taskflow.databinding.FragmentFolderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class FolderFragment : Fragment() {

    private var _binding: FragmentFolderBinding? = null
    private val binding get() = _binding!!
    private lateinit var folderList: ArrayList<FolderDataClass>
    private lateinit var folderAdapter: FolderAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFolderBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView and load folder list
        recyclerView = binding.recyclerView
        loadFolderList()

        // Setup RecyclerView
        folderAdapter = FolderAdapter(requireContext(), folderList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.adapter = folderAdapter

        binding.addFolder.setOnClickListener {
            val intent = Intent(requireContext(), CreateFolder::class.java)
            startActivityForResult(intent, 100)
        }

        return view
    }

    private fun loadFolderList() {
        val sharedPreferences = requireContext().getSharedPreferences("com.example.taskflow.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("folders", null)
        folderList = if (json != null) {
            val type = object : TypeToken<List<FolderDataClass>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            val newFolderJson = data?.getStringExtra("newFolder")
            val newFolder = Gson().fromJson(newFolderJson, FolderDataClass::class.java)

            val selectedColorResId = data?.getIntExtra("selectedColor", R.color.white)


            folderList.add(newFolder)
            folderAdapter.notifyDataSetChanged()


            saveFolderList()

        }
    }

    private fun saveFolderList() {
        val sharedPreferences = requireContext().getSharedPreferences("com.example.taskflow.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("folders", Gson().toJson(folderList))
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
