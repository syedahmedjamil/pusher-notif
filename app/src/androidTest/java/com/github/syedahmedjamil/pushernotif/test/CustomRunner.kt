package com.github.syedahmedjamil.pushernotif.test

import android.app.Application
import android.content.Context
import com.github.syedahmedjamil.pushernotif.BaseApplication
import dagger.hilt.android.testing.CustomTestApplication
import io.cucumber.android.runner.CucumberAndroidJUnitRunner

@CustomTestApplication(BaseApplication::class)
interface CustomHiltTestApplication

class CustomRunner : CucumberAndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, CustomHiltTestApplication_Application::class.java.name, context)
    }
}