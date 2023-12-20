package com.github.syedahmedjamil.pushernotif

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
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
        val addInterestUseCase: AddInterestUseCase = appContainer.addInterestUseCase
        val addInterestUseCase2: AddInterestUseCase = appContainer.addInterestUseCase
        assertNotNull(addInterestUseCase)
        assertEquals(addInterestUseCase, addInterestUseCase2)
    }

    @Test
    fun test_appContainer_has_a_getInterestsUseCase_singleton() {
        val getInterestsUseCase: GetInterestsUseCase = appContainer.getInterestsUseCase
        val getInterestsUseCase2: GetInterestsUseCase = appContainer.getInterestsUseCase
        assertNotNull(getInterestsUseCase)
        assertEquals(getInterestsUseCase, getInterestsUseCase2)
    }
}