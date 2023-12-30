package com.github.syedahmedjamil.pushernotif

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.shared_test.FakeAddInterestUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.FakeGetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.FakeRemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceViewModel
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstanceViewModelTest {
    private lateinit var viewModel: InstanceViewModel
    private lateinit var addInterestUseCase: FakeAddInterestUseCase
    private lateinit var getInterestsUseCase: FakeGetInterestsUseCase
    private lateinit var removeInterestUseCase: FakeRemoveInterestUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        addInterestUseCase = FakeAddInterestUseCase()
        getInterestsUseCase = FakeGetInterestsUseCase()
        removeInterestUseCase = FakeRemoveInterestUseCase()
        viewModel =
            InstanceViewModel(addInterestUseCase, getInterestsUseCase, removeInterestUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should add interest`() = runTest {
        // given
        addInterestUseCase.isError = false
        val interest = "interest"
        // when
        viewModel.addInterest(interest)
        advanceUntilIdle()
        val result = addInterestUseCase.result
        // then
        assertTrue(result is Result.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should get interests`() = runTest {
        // given
        val expected1 = emptyList<String>()
        val expected2 = listOf("interest1", "interest2", "interest3")
        // when
        val actual = mutableListOf<List<String>>()
        viewModel.interests.observeForever { list -> actual.add(list) }
        advanceUntilIdle()
        getInterestsUseCase.emit(expected1)
        getInterestsUseCase.emit(expected2)
        // then
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
        assertEquals(2, actual.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display error when interest is not added`() = runTest {
        // given
        addInterestUseCase.isError = true
        val interest = "interest1"
        val expected = "error"
        // when
        viewModel.addInterest(interest)
        advanceUntilIdle()
        val result = addInterestUseCase.result
        val actual = viewModel.errorMessage.value
        // then
        assertTrue(result is Result.Error)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should remove interest`() = runTest {
        // given
        removeInterestUseCase.isError = false
        val interest = "interest"
        // when
        viewModel.removeInterest(interest)
        advanceUntilIdle()
        val result = removeInterestUseCase.result
        // then
        assertTrue(result is Result.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display error when interest is not removed`() = runTest {
        // given
        removeInterestUseCase.isError = true
        val interest = "interest"
        val expected = "error"
        // when
        viewModel.removeInterest(interest)
        advanceUntilIdle()
        val result = removeInterestUseCase.result
        val actual = viewModel.errorMessage.value
        // then
        assertTrue(result is Result.Error)
        assertEquals(expected, actual)
    }

}