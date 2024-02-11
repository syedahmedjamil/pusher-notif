package com.github.syedahmedjamil.pushernotif.test.dsl.pages

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertEquals

class System {

    fun setInternetConnection(arg0: String) {
        val device = InstrumentationRegistry.getInstrumentation().uiAutomation
        if (arg0 == "on")
            device.executeShellCommand("cmd connectivity airplane-mode disable")
        if (arg0 == "off")
            device.executeShellCommand("cmd connectivity airplane-mode enable")

        Thread.sleep(10000)
    }

    fun assertNotificationListed() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.openNotification()
        val appName = device.findObject(UiSelector().resourceId("android:id/app_name_text")).text
        val subtext = device.findObject(UiSelector().resourceId("android:id/header_text")).text
        val title = device.findObject(UiSelector().resourceId("android:id/title")).text
        val body = device.findObject(UiSelector().resourceId("android:id/big_text")).text
        assertEquals("Pusher Notif (debug)", appName)
        assertEquals("test_subtext", subtext)
        assertEquals("test_title", title)
        assertEquals("test_body", body)
    }

    fun sendPushNotification() {
        val intent = Intent().apply {
            action = "com.google.android.c2dm.intent.RECEIVE"
            putExtra("interest", "test")
            putExtra("category", "test")
            putExtra("date", "test_date")
            putExtra("title", "test_title")
            putExtra("body", "test_body")
            putExtra("subtext", "test_subtext")
            putExtra("link", "https://github.com")
            putExtra(
                "image",
                "https://user-images.githubusercontent.com/20031479/181098478-7a170d22-94f0-43f7-8f69-b9d47873d42f.png"
            )
        }
        InstrumentationRegistry.getInstrumentation().context.sendBroadcast(intent)
        Thread.sleep(5000)
    }
}