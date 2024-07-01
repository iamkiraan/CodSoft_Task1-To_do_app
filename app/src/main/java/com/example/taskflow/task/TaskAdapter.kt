package com.example.taskflow.task


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class TaskAdapter(
    private val context: Context,
    private var folderList: ArrayList<TaskDataClass>,
    private val folderDeletedCallback: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.add_task, parent, false)
        return FolderViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folderData = folderList[position]
        holder.FolderName.text = folderData.taskName
        holder.FolderName.setTextColor(ContextCompat.getColor(context, folderData.colorResId))
        holder.deleteIcon.setOnClickListener {
            showBottomSheetDialog(position)
        }
    }

    private fun showBottomSheetDialog(position: Int) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_delete_confirmation, null)
        bottomSheetView.findViewById<Button>(R.id.confirm_delete).setOnClickListener {
            folderDeletedCallback(position)
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<Button>(R.id.cancel_delete).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val FolderName: TextView = itemView.findViewById(R.id.folderName)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteFolder)
    }
}
