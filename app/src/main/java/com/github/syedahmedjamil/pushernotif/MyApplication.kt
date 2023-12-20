package com.github.syedahmedjamil.pushernotif

import android.app.Application

class MyApplication : Application() {
    val appContainer = AppContainer(this)

}