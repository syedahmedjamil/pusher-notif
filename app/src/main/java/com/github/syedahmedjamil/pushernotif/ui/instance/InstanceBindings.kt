package com.github.syedahmedjamil.pushernotif.ui.instance

import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.BindingAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun setItems(listView: ListView, items: List<String>?) {
    items?.let {
        (listView.adapter as ArrayAdapter<String>).apply {
            clear()
            addAll(items)
        }
    }
}