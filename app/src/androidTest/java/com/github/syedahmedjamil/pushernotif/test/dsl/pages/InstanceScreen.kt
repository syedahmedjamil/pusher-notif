package com.github.syedahmedjamil.pushernotif.test.dsl.pages

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.syedahmedjamil.pushernotif.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not

// Instead of Thread.sleep() use UiDevice.wait() and IdlingResource for navigation and databinding

class InstanceScreen {

    fun addInterest(arg0: String) {
        onView(withId(R.id.instance_interest_edit_text)).perform(replaceText(arg0))
        onView(withId(R.id.instance_add_interest_button)).perform(click())
    }

    fun assertInterestListed(arg0: String) {
        onView(withId(R.id.instance_interests_list_view)).check(matches(hasDescendant(withText(arg0))))
    }

    fun assertInterestNotListed(arg0: String) {
        onView(withId(R.id.instance_interests_list_view))
            .check(matches(not(hasDescendant(withText(arg0)))))
    }

    fun removeInterest(arg0: String) {
        onData(allOf(`is`(instanceOf(String::class.java)), equalTo(arg0)))
            .onChildView(withId(R.id.item_remove_icon))
            .perform(click())
    }

    fun setInstanceId(arg0: String) {
        onView(withId(R.id.instance_id_edit_text)).perform(replaceText(arg0))
    }

    fun subscribe() {
        onView(withId(R.id.instance_subscribe_button)).perform(click())
    }
}

