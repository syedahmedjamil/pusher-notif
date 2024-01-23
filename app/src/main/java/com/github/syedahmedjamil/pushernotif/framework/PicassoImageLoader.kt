package com.github.syedahmedjamil.pushernotif.framework

import android.graphics.Bitmap
import android.util.Base64
import com.github.syedahmedjamil.pushernotif.ImageLoader
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class PicassoImageLoader : ImageLoader {

    private lateinit var icon: Bitmap
    override fun getBase64(uri: String): String {
        icon = Picasso.get().load(uri).get()
        val baos = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val base64Image = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        return base64Image
    }

    override fun getBitmap(): Bitmap {
        return icon
    }
}