package com.github.syedahmedjamil.pushernotif.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: InstanceViewModel
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}