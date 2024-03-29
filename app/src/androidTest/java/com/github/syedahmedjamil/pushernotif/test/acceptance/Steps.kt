package com.github.syedahmedjamil.pushernotif.test.acceptance

import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.github.syedahmedjamil.pushernotif.test.ActivityScenarioHolder
import com.github.syedahmedjamil.pushernotif.test.dsl.CucumberDsl
import com.github.syedahmedjamil.pushernotif.test.util.DataBindingIdlingResource
import com.github.syedahmedjamil.pushernotif.test.util.monitorActivity
import com.github.syedahmedjamil.pushernotif.ui.MainActivity
import com.github.syedahmedjamil.pushernotif.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import javax.inject.Inject

@HiltAndroidTest
class Steps(
    val scenarioHolder: ActivityScenarioHolder
) {

    private val dsl = CucumberDsl()

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
        scenarioHolder.launch(
            Intent(
                ApplicationProvider.getApplicationContext(),
                MainActivity::class.java
            )
        )
        dataBindingIdlingResource.monitorActivity(scenarioHolder.getScenario())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
//        runBlocking {
//            dataStore.edit {
//                it.clear()
//            }
//        }
    }


    @Given("I am on the {string} screen")
    fun iAmOnThePage(arg0: String) {
        dsl.ui.assertScreenTitle(arg0)
    }

    @When("I add {string} as an interest")
    fun iAddAsAnInterest(arg0: String) {
        dsl.ui.instance.addInterest(arg0)
    }

    @Then("I should see {string} as an interest")
    fun iShouldSeeAsAnInterest(arg0: String) {
        dsl.ui.instance.assertInterestListed(arg0)
    }

    @Then("I should see message {string}")
    fun iShouldSeeMessage(arg0: String) {
        dsl.ui.assertSnackBarMessage(arg0)
    }

    @When("I remove {string} as an interest")
    fun iRemoveAsAnInterest(arg0: String) {
        dsl.ui.instance.removeInterest(arg0)
    }

    @And("I should not see {string} as an interest")
    fun iShouldNotSeeAsAnInterest(arg0: String) {
        dsl.ui.instance.assertInterestNotListed(arg0)
    }

    @Given("I set {string} as instance id")
    fun iSetAsInstanceId(arg0: String) {
        dsl.ui.instance.setInstanceId(arg0)
    }

    @When("I try to subscribe")
    fun iTryToSubscribe() {
        dsl.ui.instance.subscribe()

    }

    @Given("Internet connection is turned {string}")
    fun internetConnectionIsTurned(arg0: String) {
        dsl.system.setInternetConnection(arg0)
    }

    @When("I receive push notification")
    fun iReceivePushNotification() {
        dsl.system.sendPushNotification()
    }

    @Then("I should see notification in the list")
    fun iShouldSeeNotificationInTheList() {
        dsl.ui.notification.assertNotificationListed()
    }

    @Given("I am subscribed to instance")
    fun iAmSubscribedToInstance() {
        dsl.ui.instance.setInstanceId("00000000-0000-0000-0000-000000000000")
        dsl.ui.instance.addInterest("test")
        dsl.ui.instance.subscribe()
    }

    @Then("I should see notification in status bar")
    fun iShouldSeeNotificationInStatusBar() {
        dsl.system.assertNotificationListed();
    }
}