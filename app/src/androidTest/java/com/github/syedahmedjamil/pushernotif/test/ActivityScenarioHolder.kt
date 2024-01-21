package com.github.syedahmedjamil.pushernotif.test

import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ActivityScenario
import com.github.syedahmedjamil.pushernotif.ui.MainActivity
import io.cucumber.java.After
import javax.inject.Inject

class ActivityScenarioHolder {

    private lateinit var scenario: ActivityScenario<MainActivity>


    @Inject
    lateinit var dataStore: DataStore<Preferences>

    fun launch(intent: Intent) {
        scenario = ActivityScenario.launch<MainActivity>(intent)
    }

    fun getScenario(): ActivityScenario<MainActivity> {
        return scenario
    }
    /**
     *  Close activity after scenario
     */
    @After
    fun close() {
        scenario.close()

    }
}