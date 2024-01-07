package com.github.syedahmedjamil.pushernotif.test.integration

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCase
import org.junit.Assert
import org.junit.Test

class AppContainerTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val appContainer: AppContainer = AppContainer(context)

    @Test
    fun test_appContainer_is_not_null() {
        Assert.assertNotNull(appContainer)
    }

    @Test
    fun test_appContainer_has_a_addInterestUseCase_singleton() {
        val useCase1: AddInterestUseCase = appContainer.addInterestUseCase
        val useCase2: AddInterestUseCase = appContainer.addInterestUseCase
        Assert.assertNotNull(useCase1)
        Assert.assertEquals(useCase1, useCase2)
    }

    @Test
    fun test_appContainer_has_a_getInterestsUseCase_singleton() {
        val useCase1: GetInterestsUseCase = appContainer.getInterestsUseCase
        val useCase2: GetInterestsUseCase = appContainer.getInterestsUseCase
        Assert.assertNotNull(useCase1)
        Assert.assertEquals(useCase1, useCase2)
    }

    @Test
    fun test_appContainer_has_a_removeInterestUseCase_singleton() {
        val useCase1: RemoveInterestUseCase = appContainer.removeInterestUseCase
        val useCase2: RemoveInterestUseCase = appContainer.removeInterestUseCase
        Assert.assertNotNull(useCase1)
        Assert.assertEquals(useCase1, useCase2)
    }

    @Test
    fun test_appContainer_has_a_subscribeUseCase_singleton() {
        val useCase1: SubscribeUseCase = appContainer.subscribeUseCase
        val useCase2: SubscribeUseCase = appContainer.subscribeUseCase
        Assert.assertNotNull(useCase1)
        Assert.assertEquals(useCase1, useCase2)
    }
}