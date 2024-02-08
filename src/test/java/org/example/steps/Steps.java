package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.InstancePage;
import org.example.pages.NotificationsPage;
import org.example.pages.SystemNotificationPage;
import org.example.tests.TestBase;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps extends TestBase {

    InstancePage instancePage;
    NotificationsPage notificationsPage;
    SystemNotificationPage systemNotificationPage;

    @Before
    public void before() throws MalformedURLException {
        Android_setUp();
        instancePage = new InstancePage(driver);
        notificationsPage = new NotificationsPage(driver);
        systemNotificationPage = new SystemNotificationPage(driver);
    }

    @After
    public void after() {
        tearDown();
    }

    @Given("I am subscribed to instance")
    public void iAmSubscribedToInstance() {
        instancePage.enterInstanceId("00000000-0000-0000-0000-000000000000");
        instancePage.enterInterestName("test");
        instancePage.clickAddInterestButton();
        instancePage.clickSubscribeButton();
    }

    @When("I receive push notification")
    public void iReceivePushNotification() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("adb root").waitFor();
        Runtime.getRuntime().exec(DIR_NAME + "/fcm").waitFor();
        // Runtime.getRuntime().exec("~/work/test/test/fcm").waitFor();
    }

    @Then("I should see notification in the list")
    public void iShouldSeeNotificationInTheList() {
        assertEquals("test_title", notificationsPage.getNotificationTitle());
        assertEquals("test_date", notificationsPage.getNotificationDate());
        assertEquals("test_subtext", notificationsPage.getNotificationSubtext());
    }

    @Then("I should see notification in status bar")
    public void iShouldSeeNotificationInStatusBar() {
        systemNotificationPage.openAndroidNotificationTray();
        assertEquals("test_title", systemNotificationPage.getNotificationTitle());
        assertEquals("test_body", systemNotificationPage.getNotificationBody());
        assertEquals("test_subtext", systemNotificationPage.getNotificationHeaderText());
    }
}
