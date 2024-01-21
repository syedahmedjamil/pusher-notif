package com.github.syedahmedjamil.pushernotif.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: InstanceViewModel
    private lateinit var appContainer: AppContainer
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration.Builder(R.id.instance_dest, R.id.notification_dest)
                .build()

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.notification_dest -> {
                    findViewById<TextView>(R.id.toolbar_title).text = "Notifications"
                }
                R.id.instance_dest -> {
                    findViewById<TextView>(R.id.toolbar_title).text = "Instance"
                }
            }
        }
    }
}
