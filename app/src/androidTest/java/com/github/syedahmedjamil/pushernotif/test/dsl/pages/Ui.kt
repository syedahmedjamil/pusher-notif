package com.github.syedahmedjamil.pushernotif.test.dsl.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.github.syedahmedjamil.pushernotif.R

class Ui {
    public val instance = InstanceScreen()
    public val notification = NotificationScreen()

    fun assertScreenTitle(arg0: String) {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(arg0)))
    }

    fun assertSnackBarMessage(arg0: String) {
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text))
            .check(ViewAssertions.matches(ViewMatchers.withText(arg0)))
    }
}