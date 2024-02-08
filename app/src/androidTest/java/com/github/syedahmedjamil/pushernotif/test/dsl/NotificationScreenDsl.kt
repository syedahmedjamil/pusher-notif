package com.github.syedahmedjamil.pushernotif.test.dsl

import com.github.syedahmedjamil.pushernotif.test.driver.NotificationScreenDriver

class NotificationScreenDsl {
    private val driver = NotificationScreenDriver()

    fun assertNotificationListed() {
        driver.assertNotificationListed()
    }

    fun assertInstanceIdName() {
        driver.assertInstanceIdName()

    }

    fun removeAllNotifications() {
        driver.removeAllNotifications()
    }

    fun selectInterest() {
        driver.selectInterest()
    }
}