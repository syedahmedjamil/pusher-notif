package com.github.syedahmedjamil.pushernotif.test.dsl

import com.github.syedahmedjamil.pushernotif.test.driver.InstanceScreenDriver

class InstanceScreenDsl {
    private val driver = InstanceScreenDriver()

    fun assertTitle(arg0: String) {
        driver.navigateToScreen(arg0)
    }

    fun addInterest(arg0: String) {
        driver.addInterest(arg0)
    }

    fun assertInterestListed(arg0: String) {
        driver.assertInterestListed(arg0)
    }

    fun assertMessage(arg0: String) {
        driver.assertMessage(arg0)
    }
}