package com.example.taskflow.FolderCreation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FolderAdapter(private val context: Context, private var folderList: ArrayList<FolderDataClass>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    private val sharedPreferences = context.getSharedPreferences("folders_pref", Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        loadFoldersFromPreferences()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.add_folder, parent, false)
        return FolderViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folderData = folderList[position]
        holder.FolderName.text = folderData.folderName

        holder.deleteIcon.setOnClickListener {
            showBottomSheetDialog(position)
        }
    }

    private fun showBottomSheetDialog(position: Int) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_delete_confirmation, null)

        bottomSheetView.findViewById<Button>(R.id.confirm_delete).setOnClickListener {
            if (position >= 0 && position < folderList.size) {
                folderList.removeAt(position)
                saveFoldersToPreferences()
                notifyItemRemoved(position)
                Log.d("FolderAdapter", "Item removed at position $position")
                bottomSheetDialog.dismiss()
            } else {
                Log.e(
                    "FolderAdapter",
                    "Index $position out of bounds for length ${folderList.size}"
                )
            }
        }

        bottomSheetView.findViewById<Button>(R.id.cancel_delete).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun saveFoldersToPreferences() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(folderList)
        editor.putString("folders", json)
        editor.apply()
    }

    private fun loadFoldersFromPreferences() {
        val json = sharedPreferences.getString("folders", null)
        if (json != null) {
            val type = object : TypeToken<ArrayList<FolderDataClass>>() {}.type
            folderList = gson.fromJson(json, type)
        }
    }

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val FolderName: TextView = itemView.findViewById(R.id.folderName)
        val FolderCount: TextView = itemView.findViewById(R.id.foldercount)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteFolder)
    }
}
