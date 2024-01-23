package com.github.syedahmedjamil.pushernotif.test.integration

import android.graphics.Bitmap
import com.github.syedahmedjamil.pushernotif.ImageLoader

class FakeImageLoader: ImageLoader {
    override fun getBase64(uri: String): String {
        return "base64Image"
    }

    override fun getBitmap(): Bitmap {
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }
}