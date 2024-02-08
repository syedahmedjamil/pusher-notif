package com.github.syedahmedjamil.pushernotif.ui.notification

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.syedahmedjamil.pushernotif.databinding.NotificationListItemBinding
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity


class NotificationListAdapter(
    context: Context,
    resource: Int,
    private val viewModel: NotificationViewModel
) :
    ArrayAdapter<NotificationEntity>(context, resource) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding =
            convertView?.tag as? NotificationListItemBinding ?: NotificationListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )

        val item = getItem(position)

        binding.viewmodel = viewModel
        binding.title = item?.title
        binding.date = item?.date
        binding.subtext= item?.subText
        binding.link= item?.link
        binding.notificationIcon.setImageBitmap(toBitmap(item?.image))
        return binding.root
    }

    private fun toBitmap(image: String?): Bitmap {
        val base64Image: ByteArray = Base64.decode(image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)
        return bitmap
    }
}