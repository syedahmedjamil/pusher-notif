package com.github.syedahmedjamil.pushernotif

import android.app.Application

open class BaseApplication : Application() {
    val appContainer = AppContainer(this)
}