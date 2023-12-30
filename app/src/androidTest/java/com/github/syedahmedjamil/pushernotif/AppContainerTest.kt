package com.github.syedahmedjamil.pushernotif

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AppContainerTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val appContainer: AppContainer = AppContainer(context)

    @Test
    fun test_appContainer_is_not_null() {
        assertNotNull(appContainer)
    }

    @Test
    fun test_appContainer_has_a_addInterestUseCase_singleton() {
        val useCase1: AddInterestUseCase = appContainer.addInterestUseCase
        val useCase2: AddInterestUseCase = appContainer.addInterestUseCase
        assertNotNull(useCase1)
        assertEquals(useCase1, useCase2)
    }

    @Test
    fun test_appContainer_has_a_getInterestsUseCase_singleton() {
        val useCase1: GetInterestsUseCase = appContainer.getInterestsUseCase
        val useCase2: GetInterestsUseCase = appContainer.getInterestsUseCase
        assertNotNull(useCase1)
        assertEquals(useCase1, useCase2)
    }

    @Test
    fun test_appContainer_has_a_removeInterestUseCase_singleton() {
        val useCase1: RemoveInterestUseCase = appContainer.removeInterestUseCase
        val useCase2: RemoveInterestUseCase = appContainer.removeInterestUseCase
        assertNotNull(useCase1)
        assertEquals(useCase1, useCase2)
    }
}