package com.github.syedahmedjamil.pushernotif.test.dsl

import com.github.syedahmedjamil.pushernotif.test.driver.InstanceScreenDriver

class InstanceScreenDsl {
    private val driver = InstanceScreenDriver()

    fun assertScreenTitle(arg0: String) {
        driver.assertScreenTitle(arg0)
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

    fun removeInterest(arg0: String) {
        driver.removeInterest(arg0)
    }

    fun assertInterestNotListed(arg0: String) {
        driver.assertInterestNotListed(arg0)
    }

    fun setInstanceId(arg0: String) {
        driver.setInstanceId(arg0)
    }

    fun subscribe() {
        driver.subscribe()

    }

    fun setInternetConnection(arg0: String) {
        driver.setInternetConnection(arg0)
    }
}