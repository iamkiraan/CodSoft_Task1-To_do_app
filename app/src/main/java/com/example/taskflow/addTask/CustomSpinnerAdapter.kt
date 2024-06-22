package com.example.taskflow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class CustomSpinnerAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val items: Array<String>
) : ArrayAdapter<String>(context, layoutResource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
        val itemText = view.findViewById<TextView>(R.id.spinner_text)

        itemText.text = items[position]


        return view
    }
}
