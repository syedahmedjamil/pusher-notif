package com.github.syedahmedjamil.pushernotif

import androidx.test.core.app.ActivityScenario
import com.github.syedahmedjamil.pushernotif.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun provideActivityScenario(): ActivityScenario<MainActivity> =
        ActivityScenario.launch(MainActivity::class.java)
}
