package com.github.syedahmedjamil.pushernotif.test.driver

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.github.syedahmedjamil.pushernotif.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.Matchers.hasToString
import org.hamcrest.Matchers.hasValue
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.startsWith

// Instead of Thread.sleep() use UiDevice.wait() and IdlingResource for navigation and databinding

class InstanceScreenDriver {

    fun assertScreenTitle(arg0: String) {
        Thread.sleep(3000)
        if(arg0 == "Instance")
            onView(withId(R.id.instance_toolbar)).check(matches(hasDescendant(withText(arg0))))
        if(arg0 == "Notifications")
            onView(withId(R.id.notification_toolbar)).check(matches(hasDescendant(withText(arg0))))
    }

    fun addInterest(arg0: String) {
        onView(withId(R.id.instance_interest_edit_text)).perform(replaceText(arg0))
        onView(withId(R.id.instance_add_interest_button)).perform(click())
    }

    fun assertInterestListed(arg0: String) {
        onView(withId(R.id.instance_interests_list_view)).check(matches(hasDescendant(withText(arg0))))
    }

    fun assertMessage(arg0: String) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(arg0)))
    }

    fun removeInterest(arg0: String) {
        onData(allOf(`is`(instanceOf(String::class.java)), equalTo(arg0)))
            .onChildView(withId(R.id.item_remove_icon))
            .perform(click())
    }

    fun assertInterestNotListed(arg0: String) {
        onView(withId(R.id.instance_interests_list_view))
            .check(matches(not(hasDescendant(withText(arg0)))))
    }

    fun setInstanceId(arg0: String) {
        onView(withId(R.id.instance_id_edit_text)).perform(replaceText(arg0))
    }

    fun subscribe() {
        onView(withId(R.id.instance_subscribe_button)).perform(click())
    }

    fun setInternetConnection(arg0: String) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        if (arg0 == "on")
            device.executeShellCommand("cmd connectivity airplane-mode disable")
        if (arg0 == "off")
            device.executeShellCommand("cmd connectivity airplane-mode enable")

        Thread.sleep(10000)
    }
}

