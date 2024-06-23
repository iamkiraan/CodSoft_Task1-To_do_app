package com.example.taskflow.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskflow.FolderCreation.FolderDataClass

class FolderViewModel : ViewModel() {

    // MutableLiveData to hold list of folders
    private val _folderList = MutableLiveData<ArrayList<FolderDataClass>>()

    // Expose LiveData to observe changes
    val folderList: LiveData<ArrayList<FolderDataClass>>
        get() = _folderList

    // Method to update folderList
    fun updateFolderList(newList: ArrayList<FolderDataClass>) {
        _folderList.value = newList
    }
}
