package com.example.taskflow.FolderCreation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R

class FolderAdapter(private val context: Context, private val folderList: ArrayList<FolderDataClass>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderName: TextView = itemView.findViewById(R.id.FolderName)
        val folderCount: TextView = itemView.findViewById(R.id.folderCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.add_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folderData = folderList[position]
        holder.folderName.text = folderData.folderName
        // Set other views as needed (e.g., folder count)
        holder.folderCount.text = "Tasks: ${folderData.taskList}"
    }
}
