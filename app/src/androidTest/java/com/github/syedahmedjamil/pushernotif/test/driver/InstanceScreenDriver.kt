package com.github.syedahmedjamil.pushernotif.test.driver

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceActivity
import io.cucumber.java.Before
import io.cucumber.java.BeforeAll
import org.hamcrest.Matchers.not
import org.junit.Rule


class InstanceScreenDriver {

    fun navigateToScreen(arg0: String) {
        onView(withId(R.id.instance_toolbar)).check(matches(hasDescendant(withText(arg0))))
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
}

