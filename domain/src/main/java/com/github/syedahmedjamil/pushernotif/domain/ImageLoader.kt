package com.github.syedahmedjamil.pushernotif.domain

interface ImageLoader {
    fun load(uri: String): String
}