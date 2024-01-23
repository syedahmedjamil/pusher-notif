package com.github.syedahmedjamil.pushernotif

import android.graphics.Bitmap

interface ImageLoader {
    fun getBase64(uri: String): String
    fun getBitmap(): Bitmap
}