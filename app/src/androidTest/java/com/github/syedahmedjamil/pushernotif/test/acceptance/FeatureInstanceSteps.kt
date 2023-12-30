package com.github.syedahmedjamil.pushernotif.test.acceptance

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.MyApplication
import com.github.syedahmedjamil.pushernotif.test.dsl.CucumberDsl
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceActivity
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.java.PendingException
import io.cucumber.java.en.And

class FeatureInstanceSteps {
    private val dsl = CucumberDsl()

    private lateinit var activityScenario: ActivityScenario<InstanceActivity>

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(InstanceActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
        (ApplicationProvider.getApplicationContext() as MyApplication).appContainer.reset()
    }

    @Given("I am on the {string} screen")
    fun iAmOnThePage(arg0: String) {
        dsl.instance.assertTitle(arg0)
    }

    @When("I add {string} as an interest")
    fun iAddAsAnInterest(arg0: String) {
        dsl.instance.addInterest(arg0)
    }

    @Then("I should see {string} as an interest")
    fun iShouldSeeAsAnInterest(arg0: String) {
        dsl.instance.assertInterestListed(arg0)
    }

    @Then("I should see message {string}")
    fun iShouldSeeMessage(arg0: String) {
        dsl.instance.assertMessage(arg0)
    }

    @When("I remove {string} as an interest")
    fun iRemoveAsAnInterest(arg0: String) {
        dsl.instance.removeInterest(arg0)
    }

    @And("I should not see {string} as an interest")
    fun iShouldNotSeeAsAnInterest(arg0: String) {
        dsl.instance.assertInterestNotListed(arg0)
    }
}