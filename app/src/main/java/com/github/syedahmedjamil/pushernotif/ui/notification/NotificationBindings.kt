package com.github.syedahmedjamil.pushernotif.ui.notification

import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.BindingAdapter
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun setItems(listView: ListView, items: List<NotificationEntity>?) {
    items?.let {
        (listView.adapter as ArrayAdapter<NotificationEntity>).apply {
            clear()
            addAll(items)
        }
    }
}