package com.github.syedahmedjamil.pushernotif.shared_test.fakes

import com.github.syedahmedjamil.pushernotif.domain.ImageLoader

class FakeImageLoader: ImageLoader {
    override fun load(uri: String): String {
        return "base64Image"
    }
}