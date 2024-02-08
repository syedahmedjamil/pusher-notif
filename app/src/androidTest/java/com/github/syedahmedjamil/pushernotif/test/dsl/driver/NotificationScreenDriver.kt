package com.github.syedahmedjamil.pushernotif.test.driver

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.syedahmedjamil.pushernotif.R

// Instead of Thread.sleep() use UiDevice.wait() and IdlingResource for navigation and databinding

class NotificationScreenDriver {
    fun assertNotificationListed() {
        onView(withId(R.id.notification_list_view)).check(matches(ViewMatchers.hasChildCount(1)))
        onView(withId(R.id.notification_title)).check(matches(withText("test_title")))
        onView(withId(R.id.notification_date)).check(matches(withText("test_date")))
        onView(withId(R.id.notification_subtext)).check(matches(withText("test_subtext")))
        onView(withId(R.id.notification_icon)).check(matches(ViewMatchers.isDisplayed()))
    }

    fun assertInstanceIdName() {

    }

    fun removeAllNotifications() {

    }

    fun selectInterest() {

    }
}

