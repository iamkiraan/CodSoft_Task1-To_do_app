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
        holder.FolderCount.text = "Tasks: ${folderData.taskList}"
    }
    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val FolderName: TextView = itemView.findViewById(R.id.folderName)
        val FolderCount: TextView = itemView.findViewById(R.id.foldercount)
    }
}
